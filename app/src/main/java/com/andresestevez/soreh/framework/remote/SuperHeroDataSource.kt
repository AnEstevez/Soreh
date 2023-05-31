package com.andresestevez.soreh.framework.remote

import com.andresestevez.soreh.data.models.Character
import com.andresestevez.soreh.data.datasources.RemoteDataSource
import com.andresestevez.soreh.framework.toDomain
import javax.inject.Inject
import javax.inject.Named

class SuperHeroDataSource @Inject constructor(
    private val remoteService: RemoteService,
    @Named("apiKey") private val apiKey: String,
) : RemoteDataSource {

    override suspend fun searchCharactersByName(name: String): List<Character> =
        remoteService.searchCharactersByName(
            apiKey = apiKey,
            name = name,
        ).results.map { it.toDomain() }


    override suspend fun getCharacterById(id: Int): Character =
        remoteService.getCharacterById(
            apiKey = apiKey,
            id = id,
        ).toDomain()


}



