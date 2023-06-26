package com.andresestevez.soreh.domain

import androidx.sqlite.db.SupportSQLiteQuery
import com.andresestevez.soreh.data.CharactersRepository
import javax.inject.Inject

class GetTopsUseCase @Inject constructor(private val repository: CharactersRepository) {

    suspend fun searchCharactersSuspend(query: SupportSQLiteQuery) =
        repository.searchCharactersRawSuspend(query)

    fun searchCharacters(query: SupportSQLiteQuery) = repository.searchCharactersRaw(query)
}