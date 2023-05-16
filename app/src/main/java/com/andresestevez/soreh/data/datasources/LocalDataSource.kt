package com.andresestevez.soreh.data.datasources

import com.andresestevez.soreh.data.Character
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun isEmpty() : Boolean

    fun getCharacterById(id: Int): Flow<Character>

    fun searchCharactersByName(name: String): Flow<List<Character>>

    fun getAllCharacters(maxItems: Int): Flow<List<Character>>

    suspend fun updateCharacter(character: Character)

    suspend fun insertCharactersList(characters: List<Character>)
}