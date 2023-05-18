package com.andresestevez.soreh

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.andresestevez.soreh.framework.workers.CacheWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltAndroidApp
class App : Application() , Configuration.Provider {

    @Inject lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() = Configuration.Builder()
        .setWorkerFactory(workerFactory)
        .build()

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.Default).launch {
            setupTimber()
            startCacheWorker()
        }

    }


    private fun setupTimber() {
        Timber.plant(object : Timber.DebugTree() {
            /**
             * Override [log] to modify the tag and add a "global tag" prefix to it. You can rename the String "global_tag_" as you see fit.
             */
            override fun log(
                priority: Int, tag: String?, message: String, t: Throwable?,
            ) {
                super.log(priority, "timber_$tag", message, t)
            }

            /**
             * Override [createStackElementTag] to include a append a "method name" to the tag.
             */
            override fun createStackElementTag(element: StackTraceElement): String {
                return String.format(
                    "%s:%s:%s",
                    element.fileName,
                    element.methodName,
                    element.lineNumber,
                    super.createStackElementTag(element)
                )
            }
        })
    }


    private fun startCacheWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val cacheWorkRequest = OneTimeWorkRequestBuilder<CacheWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniqueWork(
            CacheWorker.WORK_NAME,
            ExistingWorkPolicy.KEEP,
            cacheWorkRequest
        )
    }

}