package com.andresestevez.soreh.domain

import androidx.sqlite.db.SupportSQLiteQuery
import com.andresestevez.soreh.data.CharactersRepository
import javax.inject.Inject

class GetTopsUseCase @Inject constructor(private val repository: CharactersRepository) {

    suspend operator fun invoke(query: SupportSQLiteQuery) =
        repository.searchCharactersRawSuspend(query)
}