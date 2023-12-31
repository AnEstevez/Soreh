package com.andresestevez.soreh.framework.local

import androidx.sqlite.db.SimpleSQLiteQuery
import com.andresestevez.soreh.data.datasources.LocalDataSource
import com.andresestevez.soreh.data.models.Character
import com.andresestevez.soreh.framework.toDomain
import com.andresestevez.soreh.framework.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject


private const val TOTAL_CHARACTERS: Int = 731

internal class RoomDataSource @Inject constructor(private val dao: CharacterDao) : LocalDataSource {

    override suspend fun isEmpty() = dao.count() == 0

    override suspend fun isRefreshRequired(): Boolean = dao.count() < TOTAL_CHARACTERS
    override fun getCharactersByIdList(idList: List<Int>, idListOrder: String): Flow<List<Character>> =
        dao.getCharactersByIdList(idList, idListOrder)
            .map { it.map { characterEntity -> characterEntity.toDomain() } }.distinctUntilChanged()

    override fun getCharacterById(id: Int): Flow<Character> =
        dao.getCharacterById(id).map { it.toDomain() }.distinctUntilChanged()

    override fun searchCharactersRawFlow(query: String): Flow<List<Character>> =
        dao.searchCharactersRaw(SimpleSQLiteQuery(query))
            .map { it.map { characterEntity -> characterEntity.toDomain() } }.distinctUntilChanged()

    override suspend fun searchCharactersRawSuspend(query: String): List<Character> =
        dao.searchCharactersRawSuspend(SimpleSQLiteQuery(query)).map { characterEntity -> characterEntity.toDomain() }


    override suspend fun countCharactersRaw(query: String): Int =
        dao.countCharactersRaw(SimpleSQLiteQuery(query))


    override suspend fun getAllCharacters(): List<Character> {
        return dao.getAllCharacters()
            .map {  characterEntity -> characterEntity.toDomain() }
    }

    override fun getFavorites(): Flow<List<Character>> {
        return dao.getFavorites()
            .map { it.map { characterEntity -> characterEntity.toDomain() } }.distinctUntilChanged()
    }

    override suspend fun updateCharacter(character: Character) =
        dao.updateCharacter(character.toEntity().copy(dateModified = Date()))


    override suspend fun insertCharactersList(characters: List<Character>) =
        dao.insertCharactersList(characters.map { it.toEntity().copy(dateModified = Date()) })


}