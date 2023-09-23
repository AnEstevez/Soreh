package com.andresestevez.soreh.common

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.Configuration
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.andresestevez.soreh.framework.workers.CacheWorker
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class WorkManagerRule : TestRule {

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                val context = InstrumentationRegistry.getInstrumentation().targetContext
                val config = Configuration.Builder()
                    .setMinimumLoggingLevel(Log.DEBUG)
                    .setExecutor(SynchronousExecutor())
                    .build()
                WorkManagerTestInitHelper.initializeTestWorkManager(context, config)

                val cacheWorkRequest = OneTimeWorkRequestBuilder<CacheWorker>().build()

                WorkManager.getInstance(context)
                    .enqueueUniqueWork(
                        CacheWorker.WORK_NAME,
                        ExistingWorkPolicy.REPLACE,
                        cacheWorkRequest
                    )

                try {
                    base?.evaluate()
                } finally {
                    Log.d("WorkManagerRule", "Do some teardown")
                }
            }

        }
    }
}