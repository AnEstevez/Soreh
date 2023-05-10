package com.andresestevez.soreh.ui.screens.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.andresestevez.soreh.ui.SorehApp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(id: Int, onBackClick: () -> Unit) {
    SorehApp {
        Scaffold(
            topBar = {
                DetailAppBar(onBackClick)
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Detail Screen $id",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}