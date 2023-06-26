package com.andresestevez.soreh.ui.screens.characters.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andresestevez.soreh.R
import com.andresestevez.soreh.ui.screens.common.rememberSaveableMutableStateListOf


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersSearchBar(viewModel: SearchViewModel) {
    var inputText by rememberSaveable { mutableStateOf("") }
    var isSearchBarEnabled by remember { mutableStateOf(false) }
    val searchHistory = rememberSaveableMutableStateListOf<String>()
    val filters = viewModel.filters.collectAsStateWithLifecycle()

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
                modifier = Modifier.align(Alignment.CenterHorizontally),
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