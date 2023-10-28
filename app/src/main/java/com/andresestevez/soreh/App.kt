package com.andresestevez.soreh

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.andresestevez.soreh.framework.logs.LogsAndCrashReporting
import com.andresestevez.soreh.framework.workers.CacheWorker
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var logsAndCrashReporting: LogsAndCrashReporting

    override fun getWorkManagerConfiguration() = Configuration.Builder()
        .setWorkerFactory(workerFactory)
        .build()

    override fun onCreate() {
        super.onCreate()

        CoroutineScope(Dispatchers.Default).launch {
            logsAndCrashReporting.setup()
            startCacheWorker()
        }

    }

    private fun startCacheWorker() {
        val cacheWorkRequest = OneTimeWorkRequestBuilder<CacheWorker>().build()

        WorkManager.getInstance(applicationContext)
            .enqueueUniqueWork(
                CacheWorker.WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                cacheWorkRequest
            )

    }

}