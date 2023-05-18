package com.andresestevez.soreh.framework.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.andresestevez.soreh.data.CharactersRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber


@HiltWorker
class CacheWorker @AssistedInject constructor(
    @Assisted val ctx: Context,
    @Assisted params: WorkerParameters,
    private val repository: CharactersRepository,
) : CoroutineWorker(ctx, params) {

    companion object {
        const val WORK_NAME = "com.andresestevez.soreh.framework.workers.CacheWorker"
        val STRINGS_API_SEARCH = listOf("e", "a", "u", "i", "s", "o", "x", "t", "q")
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            Timber.d("CacheWorker start")
            if (repository.isRefreshRequired()) {
                Timber.d("API call start")
                cacheDataFromApi()
                Timber.d("API call end")
            }
            Timber.d("CacheWorker end")
            Result.success()
        } catch (e: Throwable) {
            Timber.e(e)
            Result.failure()
        }
    }

    private suspend fun cacheDataFromApi() {
        STRINGS_API_SEARCH.forEach { name ->
            val result = repository.getCharactersFromRemoteByName(name)
            result.onSuccess { characters -> repository.saveAll(characters) }
        }
    }

}
