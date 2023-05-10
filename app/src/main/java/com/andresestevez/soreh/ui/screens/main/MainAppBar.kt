package com.andresestevez.soreh.ui.screens.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.andresestevez.soreh.R
import com.andresestevez.soreh.ui.navigation.AppBarNavigation

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainAppBar() {
    androidx.compose.material3.TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        navigationIcon = {
            AppBarNavigation(Icons.Default.Menu) { /*TODO*/ }
        },
        actions = {
            AppBarAction(Icons.Default.Search) { /*TODO*/ }
            AppBarAction(Icons.Default.Share) { /*TODO*/ }
        }

    )
}

@Composable
private fun AppBarAction(
    imageVector: ImageVector,
    contentDescription: String? = null,
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription
        )
    }
}

