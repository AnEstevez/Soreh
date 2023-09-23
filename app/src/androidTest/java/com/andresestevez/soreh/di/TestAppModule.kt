package com.andresestevez.soreh.di

import android.app.Application
import androidx.room.Room
import androidx.work.WorkManager
import com.andresestevez.soreh.AppModule
import com.andresestevez.soreh.BuildConfig
import com.andresestevez.soreh.R
import com.andresestevez.soreh.framework.local.SorehDatabase
import com.andresestevez.soreh.framework.remote.RemoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [AppModule::class])
object TestAppModule {

    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyProvider(): String = BuildConfig.API_KEY

    @Provides
    @Singleton
    @Named("baseUrl")
    fun baseUrlProvider(application: Application): String =
        application.getString(R.string.tests_base_url)

    @Provides
    @Singleton
    fun sorehDbProvider(app: Application) =
        Room.inMemoryDatabaseBuilder(
            app,
            SorehDatabase::class.java,
        ).build()

    @Provides
    @Singleton
    fun characterDaoProvider(db: SorehDatabase) = db.characterDao()

    @Provides
    @Singleton
    fun remoteServiceProvider(
        okHttpClient: OkHttpClient,
        @Named("baseUrl") baseUrl: String,
    ): RemoteService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()

    @Provides
    @Singleton
    fun okHttpClientProvider(): OkHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(this).build()
    }

    @Provides
    @Singleton
    fun workManagerProvider(app: Application) = WorkManager.getInstance(app)

}