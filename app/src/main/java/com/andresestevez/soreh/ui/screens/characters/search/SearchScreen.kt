package com.andresestevez.soreh.ui.screens.characters.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andresestevez.soreh.R
import com.andresestevez.soreh.ui.SorehAppState
import com.andresestevez.soreh.ui.common.noRippleClickable
import com.andresestevez.soreh.ui.mainActivity
import com.andresestevez.soreh.ui.screens.characters.main.CharacterListVerticalGrid
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(mainActivity()),
    appState: SorehAppState,
    onClick: (Int) -> Unit,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val filters by viewModel.filters.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()

    BackHandler(appState.bottomSheetScaffoldState.bottomSheetState.isVisible) {
        coroutineScope.launch { appState.bottomSheetScaffoldState.bottomSheetState.hide() }
    }

    if (state.userMessage.isNotEmpty()) {
        appState.onShowUserMessage(state.userMessage)
        viewModel.dismissUserMessage()
    }

    Column(verticalArrangement = Arrangement.Top) {

        CharactersSearchBar(viewModel)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row(
                modifier = Modifier.Companion.weight(1f),
            ) {
                SortingDropdownMenuBox(filters, viewModel)
            }

            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
            ) {
                TextField(
                    value = stringResource(R.string.filters),
                    modifier = Modifier
                        .width(120.dp)
                        .noRippleClickable {
                            coroutineScope.launch {
                                appState.bottomSheetScaffoldState.bottomSheetState.expand()
                            }
                        },
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        Icon(imageVector = Icons.Outlined.Tune, contentDescription = "Filters")
                    },
                    enabled = false,
                    colors = TextFieldDefaults.colors(
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                        disabledIndicatorColor = MaterialTheme.colorScheme.surface,
                        disabledLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurface,
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    ),
                )
            }


        }

        CharacterListVerticalGrid(
            state = state,
            contentPaddingValues = PaddingValues(
                top = 0.dp,
                start = 5.dp,
                end = 5.dp,
                bottom = appState.scaffoldPadding.value.calculateBottomPadding()
            ),
            onClick = onClick,
        )

    }

}