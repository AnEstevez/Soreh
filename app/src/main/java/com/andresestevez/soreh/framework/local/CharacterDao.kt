package com.andresestevez.soreh.framework.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query("SELECT count(*) FROM character")
    suspend fun count(): Int

    @Query("SELECT * FROM character")
    suspend fun getAllCharacters(): List<CharacterEntity>

    @Query("SELECT * FROM character WHERE id = :id")
    fun getCharacterById(id: Int): Flow<CharacterEntity>

    @Query("SELECT * FROM character WHERE name LIKE '%' || :name || '%' order by name asc")
    fun searchCharactersByName(name: String): Flow<List<CharacterEntity>>

    @RawQuery(observedEntities = [CharacterEntity::class])
    fun searchCharactersRaw(query: SupportSQLiteQuery): Flow<List<CharacterEntity>>

    @RawQuery(observedEntities = [CharacterEntity::class])
    suspend fun countCharactersRaw(query: SupportSQLiteQuery): Int

    @RawQuery(observedEntities = [CharacterEntity::class])
    suspend fun searchCharactersRawSuspend(query: SupportSQLiteQuery): List<CharacterEntity>

    @Query("SELECT * FROM character WHERE id IN (:idList) ORDER BY INSTR(:idListOrder, id)")
    fun getCharactersByIdList(idList: List<Int>, idListOrder: String): Flow<List<CharacterEntity>>

    @Query("Select * FROM character WHERE bookmarked = 1 order by dateModified desc")
    fun getFavorites(): Flow<List<CharacterEntity>>

    @Update
    suspend fun updateCharacter(character: CharacterEntity)

    @Insert(onConflict = IGNORE)
    suspend fun insertCharactersList(characters: List<CharacterEntity>)

}