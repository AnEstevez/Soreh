package com.andresestevez.soreh.domain

import com.andresestevez.soreh.data.CharactersRepository
import com.andresestevez.testshared.createCharactersList
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class SearchCharactersUseCaseTest {

    private lateinit var useCase: SearchCharactersUseCase

    @Mock
    private lateinit var repository: CharactersRepository

    @Before
    fun setUp() {
        useCase = SearchCharactersUseCase(repository)
    }

    @Test
    fun `searchCharacters calls repository`(): Unit = runBlocking {
        //given
        val query = "select * from characters where name like '%name 2%' or fullname like '%fullname 2%'"

        createCharactersList(2)
        whenever(repository.searchCharactersRawSuspend(query)).thenReturn(Result.success(createCharactersList(2)))

        //when
        val result = useCase.searchCharacters(query)

        //then
        assert(result.isSuccess)
        Assert.assertEquals("name 2", result.getOrDefault(emptyList())[0].name )

    }

    @Test
    fun `countCharacters calls repository`(): Unit = runBlocking {
        //given
        val query = "select * from characters where name like '%name%' or fullname like '%fullname%'"

        createCharactersList(2)
        whenever(repository.countCharactersRaw(query)).thenReturn(Result.success(createCharactersList(1, 2, 3).size))

        //when
        val result = useCase.countCharacters(query)

        //then
        assert(result.isSuccess)
        Assert.assertEquals(3, result.getOrDefault(0))
    }
}

