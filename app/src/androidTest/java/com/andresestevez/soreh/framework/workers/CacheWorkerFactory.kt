package com.andresestevez.soreh.framework.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.andresestevez.soreh.data.CharactersRepository
import javax.inject.Inject

class CacheWorkerFactory @Inject constructor(private val repository: CharactersRepository) : WorkerFactory() {
    override fun createWorker(appContext: Context, workerClassName: String, workerParameters: WorkerParameters): ListenableWorker? {
        return CacheWorker(appContext, workerParameters, repository)
    }
}