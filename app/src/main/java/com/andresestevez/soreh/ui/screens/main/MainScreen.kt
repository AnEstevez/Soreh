package com.andresestevez.soreh.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.andresestevez.soreh.data.Character
import com.andresestevez.soreh.ui.SorehApp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onClick: (Character) -> Unit = {}) {
    SorehApp {
        Scaffold(
            topBar = {
                MainAppBar()
            }
        ) { padding ->
            CharacterListVerticalGrid(
                modifier = Modifier.padding(padding),
                onClick = onClick
            )
        }
    }
}


