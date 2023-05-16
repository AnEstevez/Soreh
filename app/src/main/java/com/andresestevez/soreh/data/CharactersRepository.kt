package com.andresestevez.soreh.data

import com.andresestevez.soreh.data.datasources.LocalDataSource
import com.andresestevez.soreh.data.datasources.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) {

    fun searchCharactersByName(name: String): Flow<List<Character>> = flow {
        emit(remoteDataSource.searchCharactersByName(name))
    }

    fun getCharacterById(id: Int): Flow<Character> = flow {
        emit(remoteDataSource.getCharacterById(id))
    }

    fun getRandomCharactersList(maxItems: Int = 20): Flow<List<Character>> = flow {

        if (localDataSource.isEmpty()) {
            val characters = remoteDataSource.searchCharactersByName("a")
            localDataSource.insertCharactersList(characters)
        }

        localDataSource.getAllCharacters(maxItems).collect {
            emit(it.shuffled().take(maxItems))
        }
    }

}