package com.andresestevez.soreh.data

import com.andresestevez.soreh.data.datasources.LocalDataSource
import com.andresestevez.soreh.data.datasources.RemoteDataSource
import com.andresestevez.soreh.data.models.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class CharactersRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) {


    fun getCharactersByIdList(
        idList: List<Int>,
        idListOrder: String,
    ): Flow<Result<List<Character>>> =
        localDataSource.getCharactersByIdList(idList, idListOrder).map { characters ->
            Result.success(characters)
        }.catch {
            Result.failure<List<Character>>(it)
        }

    fun searchCharactersRaw(query: String): Flow<Result<List<Character>>> =

        localDataSource.searchCharactersRawFlow(query).map { characters ->
            Result.success(characters)
        }.catch {
            Result.failure<List<Character>>(it)
        }

    suspend fun searchCharactersRawSuspend(query: String): Result<List<Character>> =
        try {
            Result.success(localDataSource.searchCharactersRawSuspend(query))
        } catch (t: Throwable) {
            Result.failure(t)
        }

    suspend fun countCharactersRaw(query: String): Result<Int> = try {
        Result.success(localDataSource.countCharactersRaw(query))
    } catch (t: Throwable) {
        Result.failure(t)
    }

    fun getCharacterById(id: Int): Flow<Result<Character>> =
        localDataSource.getCharacterById(id)
            .map { Result.success(it) }
            .catch {
                emit(Result.failure(it))
            }

    suspend fun getRandomCharactersList(maxItems: Int = 10): Result<List<Character>> = try {
        Result.success(localDataSource.getAllCharacters().shuffled().take(maxItems))
    } catch (t: Throwable) {
        Result.failure(t)
    }

    fun getFavorites(): Flow<Result<List<Character>>> = localDataSource.getFavorites()
        .map { characters -> Result.success(characters) }
        .catch {
            Result.failure<List<Character>>(it)
        }

    suspend fun getCharactersFromRemoteByName(name: String): Result<List<Character>> = try {
        Result.success(remoteDataSource.searchCharactersByName(name))
    } catch (t: Throwable) {
        Result.failure(t)
    }

    suspend fun updateCharacter(character: Character) {
        localDataSource.updateCharacter(character)
    }

    suspend fun saveAll(characters: List<Character>) =
        localDataSource.insertCharactersList(characters)

    suspend fun isRefreshRequired(): Boolean = localDataSource.isRefreshRequired()

}