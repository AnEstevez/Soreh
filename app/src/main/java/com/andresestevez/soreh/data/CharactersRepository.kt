package com.andresestevez.soreh.data

import com.andresestevez.soreh.data.datasources.LocalDataSource
import com.andresestevez.soreh.data.datasources.RemoteDataSource
import com.andresestevez.soreh.data.models.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject


class CharactersRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) {

    fun searchCharactersByName(name: String): Flow<Result<List<Character>>> =
        localDataSource.searchCharactersByName(name)
            .map { characters -> Result.success(characters) }
            .catch {
                Timber.e(it)
                emit(Result.failure(it))
            }

    fun getCharacterById(id: Int): Flow<Result<Character>> =
        localDataSource.getCharacterById(id)
            .map { Result.success(it) }
            .catch {
                Timber.e(it)
                emit(Result.failure(it))
            }

    fun getRandomCharactersList(maxItems: Int = 24): Flow<Result<List<Character>>> =
        localDataSource.getAllCharacters()
            .map { characters -> Result.success(characters.shuffled().take(maxItems)) }
            .catch {
                Timber.e(it)
                emit(Result.failure(it))
            }

    fun getFavorites(): Flow<Result<List<Character>>> =
        localDataSource.getFavorites().map { characters ->
            Result.success(characters)
        }.catch {
            Timber.e(it)
            Result.failure<List<Character>>(it)
        }

    suspend fun getCharactersFromRemoteByName(name: String): Result<List<Character>> = try {
        Result.success(remoteDataSource.searchCharactersByName(name))
    } catch (t: Throwable) {
        Timber.e(t)
        Result.failure(t)
    }

    suspend fun updateCharacter(character: Character) =
        localDataSource.updateCharacter(character)

    suspend fun saveAll(characters: List<Character>) =
        localDataSource.insertCharactersList(characters)

    suspend fun isRefreshRequired(): Boolean = localDataSource.isRefreshRequired()

}