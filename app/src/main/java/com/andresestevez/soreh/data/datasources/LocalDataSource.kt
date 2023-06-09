package com.andresestevez.soreh.data.datasources

import androidx.sqlite.db.SupportSQLiteQuery
import com.andresestevez.soreh.data.models.Character
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun isEmpty() : Boolean

    suspend fun isRefreshRequired() : Boolean

    fun getCharacterById(id: Int): Flow<Character>

    suspend fun searchCharactersRawSuspend(query: SupportSQLiteQuery): List<Character>

    suspend fun countCharactersRaw(query: SupportSQLiteQuery): Int

    fun getAllCharacters(): Flow<List<Character>>

    fun getFavorites(): Flow<List<Character>>

    suspend fun updateCharacter(character: Character)

    suspend fun insertCharactersList(characters: List<Character>)
}