package com.andresestevez.soreh.domain

import com.andresestevez.soreh.data.models.Character
import javax.inject.Inject

class SearchCharactersUseCase @Inject constructor(private val repository: com.andresestevez.soreh.data.CharactersRepository) {

    suspend fun searchCharacters(query: String): Result<List<Character>> =
        repository.searchCharactersRawSuspend(query)

    suspend fun countCharacters(query: String): Result<Int> =
        repository.countCharactersRaw(query)

}