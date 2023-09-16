package com.andresestevez.soreh.common

import com.andresestevez.soreh.data.CharactersRepository
import com.andresestevez.soreh.framework.workers.CacheWorker
import javax.inject.Inject

class Helpers @Inject constructor(private val repository : CharactersRepository) {

    suspend fun populateBDFromRemote() {
        CacheWorker.STRINGS_API_SEARCH.forEach { name ->
            val result = repository.getCharactersFromRemoteByName(name)
            result.onSuccess { characters -> repository.saveAll(characters) }
        }
    }

}
