package com.andresestevez.soreh.data

import com.andresestevez.soreh.data.datasources.LocalDataSource
import com.andresestevez.soreh.data.datasources.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
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

    fun getRandomCharactersList(maxItems: Int = 20): Flow<Result<List<Character>>> = flow {

        // TODO check if there are 731 characters in the DB (all the existing data from the API), if not request the api
        if (localDataSource.isEmpty()) {
            val characters = remoteDataSource.searchCharactersByName("a")
            localDataSource.insertCharactersList(characters)
        }

        localDataSource.getAllCharacters(maxItems).collect {
            emit(Result.success(it.shuffled().take(maxItems)))
        }
    }.catch {
        Timber.e(it)
        emit(Result.failure(it))
    }

}