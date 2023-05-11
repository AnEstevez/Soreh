package com.andresestevez.soreh.framework.remote

import com.andresestevez.soreh.data.Character
import com.andresestevez.soreh.data.datasources.RemoteDataSource
import com.andresestevez.soreh.framework.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SuperHeroDataSource(
    private val remoteService: RemoteService,
    private val apiKey: String,
) : RemoteDataSource {

    override suspend fun searchCharactersByName(name: String): List<Character> =
        withContext(Dispatchers.IO) {
            remoteService.searchCharactersByName(
                apiKey = apiKey,
                name = name,
            ).results.map { it.toDomain() }
        }

    override suspend fun getCharacterById(id: Int): Character =
        withContext(Dispatchers.IO) {
            remoteService.getCharacterById(
                apiKey = apiKey,
                id = id,
            ).toDomain()
        }

}



