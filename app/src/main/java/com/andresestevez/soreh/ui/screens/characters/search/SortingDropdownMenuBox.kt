package com.andresestevez.soreh.ui.screens.characters.search

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.ArrowDropUp
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SortingDropdownMenuBox(
    filters: CharactersFilter,
    viewModel: SearchViewModel,
) {
    val dropdownMenuOptions = listOf(
        Pair(SortingField.Intelligence, SortingDirection.Desc),
        Pair(SortingField.Intelligence, SortingDirection.Asc),
        Pair(SortingField.Strength, SortingDirection.Desc),
        Pair(SortingField.Strength, SortingDirection.Asc),
        Pair(SortingField.Speed, SortingDirection.Desc),
        Pair(SortingField.Speed, SortingDirection.Asc),
        Pair(SortingField.Durability, SortingDirection.Desc),
        Pair(SortingField.Durability, SortingDirection.Asc),
        Pair(SortingField.Power, SortingDirection.Desc),
        Pair(SortingField.Power, SortingDirection.Asc),
        Pair(SortingField.Combat, SortingDirection.Desc),
        Pair(SortingField.Combat, SortingDirection.Asc),
    )

    var sortMenuExpanded by remember { mutableStateOf(false) }
    var sortItemSelected by rememberSaveable { mutableIntStateOf(9) }

    ExposedDropdownMenuBox(
        expanded = sortMenuExpanded,
        onExpandedChange = { sortMenuExpanded = it },
        modifier = Modifier.wrapContentWidth()
    ) {

        TextField(
            value = dropdownMenuOptions[sortItemSelected].first.value.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            },
            singleLine = true,
            modifier = Modifier.menuAnchor().padding(start = 10.dp),
            onValueChange = {},
            readOnly = true,
            leadingIcon = {
                Row{
                    Icon(
                        imageVector = Icons.Outlined.Sort,
                        contentDescription = "Sort"
                    )
                    Icon(
                        imageVector = if (dropdownMenuOptions[sortItemSelected].second == SortingDirection.Asc) Icons.Outlined.ArrowDropUp else Icons.Outlined.ArrowDropDown,
                        contentDescription = null
                    )
                }

            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = MaterialTheme.colorScheme.surface,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.surface,
                focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
            )
        )


        ExposedDropdownMenu(
            expanded = sortMenuExpanded,
            onDismissRequest = { sortMenuExpanded = false }) {

            dropdownMenuOptions.forEachIndexed { index, pair ->

                DropdownMenuItem(
                    text = {
                        Text(pair.first.value.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.getDefault()
                            ) else it.toString()
                        })
                    },
                    modifier = Modifier.height(40.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = if (pair.second == SortingDirection.Asc) Icons.Outlined.ArrowDropUp else Icons.Outlined.ArrowDropDown,
                            contentDescription = null
                        )
                    },
                    onClick = {
                        sortItemSelected = index
                        sortMenuExpanded = false
                        filters.sort = Pair(
                            pair.first,
                            pair.second
                        )
                        viewModel.searchCharacters(
                            CharactersQueryBuilder(
                                filters,
                                false
                            ).build()
                        )
                    }
                )
            }
        }
    }
}