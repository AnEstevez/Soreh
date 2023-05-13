package com.andresestevez.soreh.ui.screens.characters.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.andresestevez.soreh.data.CharactersRepository
import com.andresestevez.soreh.domain.GetRandomCharactersListUseCase
import com.andresestevez.soreh.framework.remote.RemoteClient
import com.andresestevez.soreh.framework.remote.SuperHeroDataSource
import com.andresestevez.soreh.ui.SorehApp
import com.andresestevez.soreh.ui.screens.characters.ItemUiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onClick: (ItemUiState) -> Unit = {}) {
    SorehApp {

        val getRandomCharactersListUseCase = GetRandomCharactersListUseCase(
            CharactersRepository(
                remoteDataSource = SuperHeroDataSource(
                    apiKey = "",
                    remoteService = RemoteClient.service
                )
            )
        )

        val viewModel = CharactersViewModel(getRandomCharactersListUseCase)

        val state by viewModel.state.collectAsState()

        Scaffold(
            topBar = {
                MainAppBar()
            }
        ) { padding ->

            CharacterListVerticalGrid(
                modifier = Modifier.padding(padding),
                state = state,
                onClick = onClick
            )
        }
    }

}


