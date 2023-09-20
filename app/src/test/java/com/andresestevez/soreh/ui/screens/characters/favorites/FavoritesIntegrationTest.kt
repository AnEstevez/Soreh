package com.andresestevez.soreh.ui.screens.characters.favorites

import app.cash.turbine.test
import com.andresestevez.soreh.CoroutinesTestRule
import com.andresestevez.soreh.FakeLocalDatasource
import com.andresestevez.soreh.FakeRemoteDatasource
import com.andresestevez.soreh.data.CharactersRepository
import com.andresestevez.soreh.domain.GetFavoritesUseCase
import com.andresestevez.soreh.ui.screens.common.UiState
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoritesIntegrationTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var vm: FavoritesViewModel
    private lateinit var repository: CharactersRepository
    private lateinit var useCase: GetFavoritesUseCase

    @Before
    fun setUp() {
        repository = CharactersRepository(FakeRemoteDatasource(), FakeLocalDatasource())
        useCase = GetFavoritesUseCase(repository)
    }

    @Test
    fun `favorites characters retrieved from localDatasource`() = runTest {
        //given
        vm = FavoritesViewModel(useCase)

        //when
        vm.state.test {

            //then
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(loading = true), awaitItem())
            assert(awaitItem().data.all { it.character.bookmarked })
        }
    }

}