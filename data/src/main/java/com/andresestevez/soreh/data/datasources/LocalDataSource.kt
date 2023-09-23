package com.andresestevez.soreh.data.datasources

import com.andresestevez.soreh.data.models.Character
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun isEmpty() : Boolean

    suspend fun isRefreshRequired() : Boolean

    fun getCharactersByIdList(idList: List<Int>, idListOrder: String): Flow<List<Character>>

    fun getCharacterById(id: Int): Flow<Character>

    fun searchCharactersRawFlow(query: String): Flow<List<Character>>

    suspend fun searchCharactersRawSuspend(query: String): List<Character>

    suspend fun countCharactersRaw(query: String): Int

    suspend fun getAllCharacters(): List<Character>

    fun getFavorites(): Flow<List<Character>>

    suspend fun updateCharacter(character: Character)

    suspend fun insertCharactersList(characters: List<Character>)
}