package com.andresestevez.soreh.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.andresestevez.soreh.ui.theme.SorehTheme

@Composable
fun SorehApp(content: @Composable () -> Unit) {
    SorehTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}