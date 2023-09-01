package com.andresestevez.soreh.domain

import com.andresestevez.soreh.data.CharactersRepository
import javax.inject.Inject

class GetTopsUseCase @Inject constructor(private val repository: CharactersRepository) {

    suspend fun searchCharactersSuspend(query: String) =
        repository.searchCharactersRawSuspend(query)

    fun searchCharacters(query: String) = repository.searchCharactersRaw(query)
}