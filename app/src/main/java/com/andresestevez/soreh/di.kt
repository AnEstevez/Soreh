package com.andresestevez.soreh

import android.app.Application
import androidx.room.Room
import androidx.work.WorkManager
import com.andresestevez.soreh.data.datasources.LocalDataSource
import com.andresestevez.soreh.data.datasources.RemoteDataSource
import com.andresestevez.soreh.framework.analytics.AnalyticsHelper
import com.andresestevez.soreh.framework.analytics.FirebaseAnalyticsHelper
import com.andresestevez.soreh.framework.local.RoomDataSource
import com.andresestevez.soreh.framework.local.SorehDatabase
import com.andresestevez.soreh.framework.remote.RemoteService
import com.andresestevez.soreh.framework.remote.SuperHeroDataSource
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyProvider(): String = BuildConfig.API_KEY

    @Provides
    @Singleton
    @Named("baseUrl")
    fun baseUrlProvider(application: Application): String =
        application.getString(R.string.superhero_base_url)

    @Provides
    @Singleton
    fun okHttpClientProvider(): OkHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(this).build()
    }

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
    fun sorehDbProvider(app: Application) =
        Room.databaseBuilder(
            app,
            SorehDatabase::class.java,
            "soreh-db"
        ).build()

    @Provides
    @Singleton
    fun characterDaoProvider(db: SorehDatabase) = db.characterDao()


    @Provides
    @Singleton
    fun workManagerProvider(app: Application) = WorkManager.getInstance(app)

    @Provides
    @Singleton
    fun crashlyticsProvider() = FirebaseCrashlytics.getInstance()

}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {

    @Binds
    internal abstract fun bindRemoteDataSource(remoteDataSource: SuperHeroDataSource): RemoteDataSource

    @Binds
    internal abstract fun bindLocalDataSource(localDataSource: RoomDataSource): LocalDataSource

}

@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyticsModule {
    @Binds
    abstract fun bindsAnalyticsHelper(analyticsHelperImpl: FirebaseAnalyticsHelper): AnalyticsHelper

    companion object {
        @Provides
        @Singleton
        fun provideFirebaseAnalytics(): FirebaseAnalytics { return Firebase.analytics }
    }
}