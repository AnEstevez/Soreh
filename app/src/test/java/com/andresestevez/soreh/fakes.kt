package com.andresestevez.soreh

import com.andresestevez.soreh.data.datasources.LocalDataSource
import com.andresestevez.soreh.data.datasources.RemoteDataSource
import com.andresestevez.soreh.data.models.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

val initialCharactersList = mutableListOf(
    Character(
        id = 1,
        name = "Superman",
        fullName = "Clark Kent",
        gender = "male",
        publisher = "DC Comics",
        intelligence = 90,
        bookmarked = true
    ),
    Character(
        id = 2,
        name = "Hulk",
        fullName = "Bruce Banner",
        gender = "male",
        publisher = "Marvel Comics",
        intelligence = 90,
        bookmarked = false
    ),
    Character(
        id = 3,
        name = "Supergirl",
        fullName = "Kara Zor-El",
        gender = "female",
        publisher = "DC Comics",
        intelligence = 94,
        bookmarked = false
    ),
    Character(
        id = 4,
        name = "Poison Ivy",
        fullName = "Pamela Isley",
        gender = "female",
        publisher = "DC Comics",
        intelligence = 81,
        bookmarked = true
    ),
    Character(
        id = 5,
        name = "Spider-Man",
        fullName = "Peter Parker",
        gender = "male",
        publisher = "Marvel Comics",
        intelligence = 90,
        bookmarked = true
    ),
    Character(
        id = 6,
        name = "Iron Man",
        fullName = "Tony Stark",
        gender = "male",
        publisher = "Marvel Comics",
        intelligence = 100,
        bookmarked = false
    ),
)

class FakeLocalDatasource(charactersList: List<Character> = initialCharactersList) :
    LocalDataSource {

    private val characters: List<Character> = charactersList

    override suspend fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun isRefreshRequired(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getCharactersByIdList(
        idList: List<Int>,
        idListOrder: String,
    ): Flow<List<Character>> {
        TODO("Not yet implemented")
    }

    override fun getCharacterById(id: Int): Flow<Character> {
        TODO("Not yet implemented")
    }

    override fun searchCharactersRawFlow(query: String): Flow<List<Character>> {
        TODO("Not yet implemented")
    }

    override suspend fun searchCharactersRawSuspend(query: String): List<Character> {
        TODO("Not yet implemented")
    }

    override suspend fun countCharactersRaw(query: String): Int {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCharacters(): List<Character> {
        TODO("Not yet implemented")
    }

    override fun getFavorites(): Flow<List<Character>> = flowOf(characters.filter { it.bookmarked })

    override suspend fun updateCharacter(character: Character) {
        TODO("Not yet implemented")
    }

    override suspend fun insertCharactersList(characters: List<Character>) {
        TODO("Not yet implemented")
    }

}

class FakeRemoteDatasource() : RemoteDataSource {
    override suspend fun searchCharactersByName(name: String): List<Character> {
        TODO("Not yet implemented")
    }

    override suspend fun getCharacterById(id: Int): Character {
        TODO("Not yet implemented")
    }

}