package com.andresestevez.soreh.ui.screens.characters.search

import app.cash.turbine.test
import com.andresestevez.soreh.CoroutinesTestRule
import com.andresestevez.soreh.domain.SearchCharactersUseCase
import com.andresestevez.soreh.ui.screens.common.ItemUiState
import com.andresestevez.soreh.ui.screens.common.UiState
import com.andresestevez.testshared.createCharactersList
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var useCase: SearchCharactersUseCase

    private lateinit var vm: SearchViewModel

    private var charactersList =
        createCharactersList(91, 92, 93).map { it.copy(bookmarked = true) }
    @Test
    fun `targetCharactersState is updated after calling countTargetCharacters`() = runTest {
        //given
        whenever(useCase.countCharacters(any())).thenReturn(Result.success(100))
        vm = SearchViewModel(useCase)

        //when
        vm.countTargetCharacters("select * from characters")
        vm.targetCharactersState.test {

            //then
            assertEquals(TargetCharactersUiState(), awaitItem())
            assertEquals(TargetCharactersUiState(loading = true), awaitItem())
            assertEquals(TargetCharactersUiState(data = 100), awaitItem())
        }
    }

    @Test
    fun `targetCharactersState is updated when useCase returns fail`() = runTest {
        //given
        whenever(useCase.countCharacters(any())).thenReturn(Result.failure(IOException()))
        vm = SearchViewModel(useCase)

        //when
        vm.countTargetCharacters("select * from characters")
        vm.targetCharactersState.test {

            //then
            assertEquals(TargetCharactersUiState(), awaitItem())
            assertEquals(TargetCharactersUiState(loading = true), awaitItem())
            assertEquals(
                TargetCharactersUiState(userMessage = "Soreh is offline: Check your internet connection"),
                awaitItem()
            )
        }
    }


    @Test
    fun `state is updated after calling searchCharacters`() = runTest {
        //given
        whenever(useCase.searchCharacters(any())).thenReturn(Result.success(charactersList))
        vm = SearchViewModel(useCase)

        //when
        vm.searchCharacters("select * from characters")
        vm.state.test {

            //then
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(UiState(data = charactersList.map{ ItemUiState(it) }), awaitItem())
        }
    }

    @Test
    fun `state is updated when useCase returns fail`() = runTest {
        //given
        whenever(useCase.searchCharacters(any())).thenReturn(Result.failure(IOException()))
        vm = SearchViewModel(useCase)

        //when
        vm.searchCharacters("select * from characters")
        vm.state.test {

            //then
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(
                UiState(userMessage = "Soreh is offline: Check your internet connection"),
                awaitItem()
            )
        }
    }
}