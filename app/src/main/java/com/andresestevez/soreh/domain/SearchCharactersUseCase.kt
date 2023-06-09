package com.andresestevez.soreh.domain

import androidx.sqlite.db.SupportSQLiteQuery
import com.andresestevez.soreh.data.CharactersRepository
import com.andresestevez.soreh.data.models.Character
import javax.inject.Inject

class SearchCharactersUseCase @Inject constructor(private val repository: CharactersRepository) {

    suspend fun searchCharacters(query: SupportSQLiteQuery): Result<List<Character>> =
        repository.searchCharactersRawSuspend(query)

    suspend fun countCharacters(query: SupportSQLiteQuery): Result<Int> =
        repository.countCharactersRaw(query)

}