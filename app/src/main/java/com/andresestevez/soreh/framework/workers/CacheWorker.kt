package com.andresestevez.soreh.framework.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.andresestevez.soreh.data.CharactersRepository
import com.andresestevez.soreh.data.models.Character
import com.andresestevez.soreh.framework.analytics.AnalyticsEvent
import com.andresestevez.soreh.framework.analytics.AnalyticsEvent.Types.Companion.WORKER_API_CALL
import com.andresestevez.soreh.framework.analytics.AnalyticsHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import timber.log.Timber


@HiltWorker
class CacheWorker @AssistedInject constructor(
    @Assisted val ctx: Context,
    @Assisted params: WorkerParameters,
    private val repository: CharactersRepository,
    private val analyticsHelper: AnalyticsHelper
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
                analyticsHelper.logEvent(AnalyticsEvent(WORKER_API_CALL))
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
        val deferredRemoteCharacters = mutableListOf<Deferred<kotlin.Result<List<Character>>>>()

        supervisorScope {
            STRINGS_API_SEARCH.forEach { name ->
                val result = async(CoroutineExceptionHandler { _, throwable ->
                    Timber.e(throwable, message = "Api call for name [${name}] failed")
                }) { repository.getCharactersFromRemoteByName(name) }
                deferredRemoteCharacters.add(result)
            }

            val remoteCharacters = deferredRemoteCharacters.awaitAll()
            remoteCharacters.forEach { result ->
                async(CoroutineExceptionHandler { _, throwable ->
                    Timber.e(throwable)
                }) {
                    result.onSuccess { characters -> repository.saveAll(characters) }
                        .onFailure { Timber.e(it) }
                }
            }
        }
    }

}
