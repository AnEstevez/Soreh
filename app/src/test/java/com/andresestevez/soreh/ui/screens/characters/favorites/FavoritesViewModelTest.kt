package com.andresestevez.soreh.ui.screens.characters.favorites

import app.cash.turbine.test
import com.andresestevez.soreh.CoroutinesTestRule
import com.andresestevez.soreh.domain.GetFavoritesUseCase
import com.andresestevez.soreh.ui.screens.common.ItemUiState
import com.andresestevez.soreh.ui.screens.common.UiState
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
class FavoritesViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var useCase: GetFavoritesUseCase

    private var favoriteCharacters =
        createCharactersList(91, 92, 93).map { it.copy(bookmarked = true) }

    private lateinit var vm: FavoritesViewModel

    @Test
    fun `State is updated with current cached content immediately`() = runTest {
        //given
        whenever(useCase()).thenReturn(flowOf(Result.success(favoriteCharacters)))
        vm = FavoritesViewModel(useCase)

        //when
        vm.state.test {

            //then
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(UiState(data = favoriteCharacters.map { ItemUiState(it) }), awaitItem())
        }
    }

    @Test
    fun `State user message is updated when use case returns fail`(): Unit = runTest {
        //given
        whenever(useCase()).thenReturn(flowOf(Result.failure(IOException())))
        vm = FavoritesViewModel(useCase)

        //when
        vm.state.test {

            //then
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            assertEquals(
                UiState(
                    loading = false,
                    userMessage = "Soreh is offline: Check your internet connection"
                ),
                awaitItem()
            )
        }
    }

}