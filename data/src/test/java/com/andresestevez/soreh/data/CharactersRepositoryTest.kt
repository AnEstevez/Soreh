package com.andresestevez.soreh.data

import com.andresestevez.soreh.data.datasources.LocalDataSource
import com.andresestevez.soreh.data.datasources.RemoteDataSource
import com.andresestevez.testshared.createCharactersList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class CharactersRepositoryTest {

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Mock
    lateinit var localDataSource: LocalDataSource

    private lateinit var repository: CharactersRepository

    @Before
    fun setUp() {
        repository = CharactersRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun `searchCharactersRawSuspend returns failure when localDataSource throws exception`(): Unit = runBlocking {
        // given
        whenever(localDataSource.searchCharactersRawSuspend(any())).thenThrow(RuntimeException())

        // when
        val result = repository.searchCharactersRawSuspend("Select * from character")

        // then
        assertEquals(true, result.isFailure)

    }

    @Test
    fun `searchCharactersRawSuspend returns data from localDataSource`(): Unit = runBlocking {
        // given
        whenever(localDataSource.searchCharactersRawSuspend(any())).thenReturn(
            createCharactersList(1,2,3)
        )

        // when
        val result = repository.searchCharactersRawSuspend("Select * from character")

        // then
        assertEquals(true, result.isSuccess)
        assertEquals(3, result.getOrDefault(emptyList()).size)

    }
}

