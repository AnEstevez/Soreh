package com.andresestevez.soreh.ui.screens.characters.detail

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.andresestevez.soreh.ui.navigation.AppBarNavigation

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DetailAppBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = {        },
        navigationIcon = {
            AppBarNavigation(Icons.Default.ArrowBack) { onBackClick() }
        },
    )
}

