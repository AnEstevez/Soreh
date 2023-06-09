package com.andresestevez.soreh.ui.screens.characters.search

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.andresestevez.soreh.R
import com.andresestevez.soreh.ui.SorehAppState
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

    val uiState by viewModel.state.collectAsState()
    val filters by viewModel.filters.collectAsState()
    var sortMenuExpanded by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    BackHandler(appState.bottomSheetScaffoldState.bottomSheetState.isVisible) {
        coroutineScope.launch { appState.bottomSheetScaffoldState.bottomSheetState.hide() }
    }

    Column() {

        CharactersSearchBar(viewModel)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = { sortMenuExpanded = !sortMenuExpanded }) {
                Icon(imageVector = Icons.Outlined.Sort, contentDescription = "Sort")
            }
            DropdownMenu(
                expanded = sortMenuExpanded,
                onDismissRequest = { sortMenuExpanded = false }) {
                SorehDropdownMenuItem(R.string.intelligence, Icons.Outlined.ArrowDropDown) {
                    filters.sort = Pair(SortingField.Intelligence, SortingDirection.Desc)
                    viewModel.searchCharacters(CharactersQueryBuilder(filters, false).build())
                }
                SorehDropdownMenuItem(R.string.intelligence, Icons.Outlined.ArrowDropUp) {
                    filters.sort = Pair(SortingField.Intelligence, SortingDirection.Asc)
                    viewModel.searchCharacters(CharactersQueryBuilder(filters, false).build())
                }

                SorehDropdownMenuItem(R.string.strength, Icons.Outlined.ArrowDropDown) {
                    filters.sort = Pair(SortingField.Strength, SortingDirection.Desc)
                    viewModel.searchCharacters(CharactersQueryBuilder(filters, false).build())
                }
                SorehDropdownMenuItem(R.string.strength, Icons.Outlined.ArrowDropUp) {
                    filters.sort = Pair(SortingField.Strength, SortingDirection.Asc)
                    viewModel.searchCharacters(CharactersQueryBuilder(filters, false).build())
                }

                SorehDropdownMenuItem(R.string.speed, Icons.Outlined.ArrowDropDown) {
                    filters.sort = Pair(SortingField.Speed, SortingDirection.Desc)
                    viewModel.searchCharacters(CharactersQueryBuilder(filters, false).build())
                }
                SorehDropdownMenuItem(R.string.speed, Icons.Outlined.ArrowDropUp) {
                    filters.sort = Pair(SortingField.Speed, SortingDirection.Asc)
                    viewModel.searchCharacters(CharactersQueryBuilder(filters, false).build())
                }

                SorehDropdownMenuItem(R.string.durability, Icons.Outlined.ArrowDropDown) {
                    filters.sort = Pair(SortingField.Durability, SortingDirection.Desc)
                    viewModel.searchCharacters(CharactersQueryBuilder(filters, false).build())
                }
                SorehDropdownMenuItem(R.string.durability, Icons.Outlined.ArrowDropUp) {
                    filters.sort = Pair(SortingField.Durability, SortingDirection.Asc)
                    viewModel.searchCharacters(CharactersQueryBuilder(filters, false).build())
                }

                SorehDropdownMenuItem(R.string.power, Icons.Outlined.ArrowDropDown) {
                    filters.sort = Pair(SortingField.Power, SortingDirection.Desc)
                    viewModel.searchCharacters(CharactersQueryBuilder(filters, false).build())
                }
                SorehDropdownMenuItem(R.string.power, Icons.Outlined.ArrowDropUp) {
                    filters.sort = Pair(SortingField.Power, SortingDirection.Asc)
                    viewModel.searchCharacters(CharactersQueryBuilder(filters, false).build())
                }

                SorehDropdownMenuItem(R.string.combat, Icons.Outlined.ArrowDropDown) {
                    filters.sort = Pair(SortingField.Combat, SortingDirection.Desc)
                    viewModel.searchCharacters(CharactersQueryBuilder(filters, false).build())
                }
                SorehDropdownMenuItem(R.string.combat, Icons.Outlined.ArrowDropUp) {
                    filters.sort = Pair(SortingField.Combat, SortingDirection.Asc)
                    viewModel.searchCharacters(CharactersQueryBuilder(filters, false).build())
                }

            }
            IconButton(onClick = {
                coroutineScope.launch {
                    appState.bottomSheetScaffoldState.bottomSheetState.expand()
                }
            }) {
                Icon(imageVector = Icons.Outlined.Tune, contentDescription = "Filter")
            }
        }

        CharacterListVerticalGrid(
            state = uiState,
            contentPaddingValues = appState.scaffoldPadding.value,
            onClick = onClick,
        )

    }
}

@Composable
private fun SorehDropdownMenuItem(
    @StringRes label: Int,
    trailIcon: ImageVector,
    onClick: () -> Unit,
) {
    DropdownMenuItem(
        text = { Text(stringResource(id = label)) },
        modifier = Modifier.height(40.dp),
        trailingIcon = {
            Icon(
                imageVector = trailIcon,
                contentDescription = null
            )
        },
        onClick = { onClick() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersSearchBar(viewModel: SearchViewModel) {
    var inputText by rememberSaveable { mutableStateOf("") }
    var isSearchBarEnabled by remember { mutableStateOf(false) }
    val searchHistory = remember { mutableStateListOf<String>() }
    val filters = viewModel.filters.collectAsState()

    Column(modifier = Modifier.padding(horizontal = 10.dp)) {


        SearchBar(modifier = Modifier.fillMaxWidth(),
            query = inputText,
            onQueryChange = {
                inputText = it
            },
            onSearch = {
                filters.value.name = inputText
                searchHistory.add(inputText)
                isSearchBarEnabled = false
                viewModel.searchCharacters(CharactersQueryBuilder(filters.value, false).build())
            },
            active = isSearchBarEnabled,
            onActiveChange = {
                isSearchBarEnabled = it
            },
            placeholder = {
                Text(text = stringResource(R.string.find_your_heroes))
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon")
            },
            trailingIcon = {
                if (isSearchBarEnabled) {
                    Icon(
                        modifier = Modifier.clickable {
                            if (inputText.isNotEmpty()) {
                                inputText = ""
                                filters.value.name = ""
                            } else {
                                isSearchBarEnabled = false
                            }
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close icon"
                    )
                }
            }
        ) {
            searchHistory.forEach {
                if (it.isNotEmpty()) {
                    Row(modifier = Modifier.padding(all = 14.dp)) {
                        Icon(imageVector = Icons.Default.History, contentDescription = null)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = it)
                    }
                }
            }

            Divider()

            AssistChip(
                modifier = Modifier.align(CenterHorizontally),
                onClick = { searchHistory.clear() },
                enabled = searchHistory.isNotEmpty(),
                label = { Text(text = stringResource(R.string.clear_history)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = null
                    )
                }
            )

        }
    }

}