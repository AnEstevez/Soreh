package com.andresestevez.soreh.data

import com.andresestevez.soreh.data.datasources.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharactersRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    fun searchCharactersByName(name: String): Flow<List<Character>> = flow {
        emit(remoteDataSource.searchCharactersByName(name))
    }

    fun getCharacterById(id: Int): Flow<Character> = flow {
        emit(remoteDataSource.getCharacterById(id))
    }

    fun getRandomCharactersList(count: Int = 20) : Flow<List<Character>> = flow {
        //TODO implement correct random query
        emit(remoteDataSource.searchCharactersByName("superman"))
    }.distinctUntilChanged()

}