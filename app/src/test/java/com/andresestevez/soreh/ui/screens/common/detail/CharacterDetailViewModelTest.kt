package com.andresestevez.soreh.ui.screens.common.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.andresestevez.soreh.CoroutinesTestRule
import com.andresestevez.soreh.domain.GetCharacterByIdUseCase
import com.andresestevez.soreh.domain.ToggleFavoriteUseCase
import com.andresestevez.soreh.ui.navigation.NavCommand
import com.andresestevez.soreh.ui.screens.common.detail.CharacterDetailViewModel.UiState
import com.andresestevez.testshared.createCharactersList
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class CharacterDetailViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var toggleFavoritesUseCase: ToggleFavoriteUseCase

    @Mock
    lateinit var getCharacterByIdUseCase: GetCharacterByIdUseCase

    @Mock
    lateinit var stateHandle: SavedStateHandle

    private lateinit var vm : CharacterDetailViewModel
    private val characterId = 13


    @Test
    fun `retrieve character by id`() = runTest {
        //given
        whenever(stateHandle.get<Int>(NavCommand.CHARACTER_ID)).thenReturn(characterId)
        whenever(getCharacterByIdUseCase(characterId)).thenReturn(flowOf(Result.success(
            createCharactersList(characterId)[0]
        )))
        vm = CharacterDetailViewModel(stateHandle, getCharacterByIdUseCase, toggleFavoritesUseCase)

        //when
        vm.state.test {

            //then
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(characterId, awaitItem().data?.character?.id ?: 0)
        }
    }

    @Test
    fun `state is updated when getCharacterByIdUseCase returns failure`() = runTest {
        //given
        whenever(stateHandle.get<Int>(NavCommand.CHARACTER_ID)).thenReturn(characterId)
        whenever(getCharacterByIdUseCase(characterId)).thenReturn(flowOf(Result.failure(
            IOException()
        )))
        vm = CharacterDetailViewModel(stateHandle, getCharacterByIdUseCase, toggleFavoritesUseCase)

        //when
        vm.state.test {

            //then
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(UiState(userMessage = "Soreh is offline: Check your internet connection"), awaitItem())
        }
    }

    @Test
    fun `dismissUserMessage deletes existing user message`() = runTest {
        //given
        whenever(stateHandle.get<Int>(NavCommand.CHARACTER_ID)).thenReturn(characterId)
        whenever(getCharacterByIdUseCase(characterId)).thenReturn(flowOf(Result.failure(
            IOException()
        )))
        vm = CharacterDetailViewModel(stateHandle, getCharacterByIdUseCase, toggleFavoritesUseCase)

        //when
        vm.state.test {

            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(UiState(userMessage = "Soreh is offline: Check your internet connection"), awaitItem())

            vm.dismissUserMessage()

            //then
            assertEquals(UiState(), awaitItem())
        }
    }
    
}