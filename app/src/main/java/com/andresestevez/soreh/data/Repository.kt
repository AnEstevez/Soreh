package com.andresestevez.soreh.data

import com.andresestevez.soreh.data.datasources.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository(val remoteDataSource: RemoteDataSource) {

    fun searchCharactersByName(name: String): Flow<List<Character>> = flow {
        emit(remoteDataSource.searchCharactersByName(name))
    }

    fun getCharacterById(id: Int): Flow<Character> = flow {
        emit(remoteDataSource.getCharacterById(id))
    }

}