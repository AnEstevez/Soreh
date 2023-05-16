package com.andresestevez.soreh.framework.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query("SELECT count(*) FROM character")
    suspend fun count(): Int

    @Query("SELECT * FROM character")
    fun getAllCharacters(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM character WHERE id = :id")
    fun getCharacterById(id: Int): Flow<CharacterEntity>

    @Query("SELECT * FROM character WHERE name LIKE '%' || :name || '%' ")
    fun searchCharactersByName(name: String): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM character WHERE id IN (:idList)")
    fun getCharactersByIdList(idList: List<Int>): Flow<List<CharacterEntity>>

    @Update
    suspend fun updateCharacter(character: CharacterEntity)

    @Insert(onConflict = IGNORE)
    suspend fun insertCharactersList(characters: List<CharacterEntity>)

}