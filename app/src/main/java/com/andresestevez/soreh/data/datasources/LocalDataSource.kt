package com.andresestevez.soreh.data.datasources

import com.andresestevez.soreh.data.models.Character
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun isEmpty() : Boolean

    suspend fun isRefreshRequired() : Boolean

    fun getCharacterById(id: Int): Flow<Character>

    fun searchCharactersByName(name: String): Flow<List<Character>>

    fun getAllCharacters(): Flow<List<Character>>

    fun getFavorites(): Flow<List<Character>>

    suspend fun updateCharacter(character: Character)

    suspend fun insertCharactersList(characters: List<Character>)
}