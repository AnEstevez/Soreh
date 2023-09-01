package com.andresestevez.soreh.data.datasources

import com.andresestevez.soreh.data.models.Character

interface RemoteDataSource {
    suspend fun searchCharactersByName(name: String): List<Character>
    suspend fun getCharacterById(id: Int): Character
}
