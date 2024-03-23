package com.andresestevez.soreh.ui.screens.characters.search

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andresestevez.soreh.R
import com.andresestevez.soreh.ui.SorehAppState
import com.andresestevez.soreh.ui.mainActivity
import com.andresestevez.soreh.ui.screens.characters.search.Alignment.*
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SorehBottomSheet(
    appState: SorehAppState,
    searchViewModel: SearchViewModel = hiltViewModel(mainActivity()),
) {

    val filters = searchViewModel.filters.collectAsStateWithLifecycle()
    val targetCharactersState = searchViewModel.targetCharactersUiState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(
                start = 20.dp,
                end = 20.dp,
                bottom = appState.navigationBarsInsetsDp.value
            )
    ) {

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onSurface,
                contentColor = MaterialTheme.colorScheme.surface,
            ),
            onClick = {
                searchViewModel.searchCharacters(
                    CharactersQueryBuilder(
                        filters.value,
                        false
                    ).build()
                )
                coroutineScope.launch {
                    appState.bottomSheetScaffoldState.bottomSheetState.hide()
                }
            }
        ) {
            Text(
                stringResource(R.string.show_x_results, targetCharactersState.value.data),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            )
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {

            Text(
                stringResource(R.string.publisher_universe),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp)
            )

            FlowRow(
                horizontalArrangement = Arrangement.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 8.dp)
                    .fillMaxWidth()
            ) {

                SorehFilterChip(label = stringResource(R.string.marvel_comics)) { isSelected ->
                    addSelection(isSelected, filters.value.publishers, Publisher.Marvel)
                    searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
                }
                SorehFilterChip(label = stringResource(R.string.dc_comics)) { isSelected ->
                    addSelection(isSelected, filters.value.publishers, Publisher.DC)
                    searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
                }
                SorehFilterChip(label = stringResource(R.string.dark_horse_comics)) { isSelected ->
                    addSelection(isSelected, filters.value.publishers, Publisher.DarkHorse)
                    searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
                }
                SorehFilterChip(label = stringResource(R.string.nbc_heroes)) { isSelected ->
                    addSelection(isSelected, filters.value.publishers, Publisher.NBC)
                    searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
                }
                SorehFilterChip(label = stringResource(R.string.george_lucas)) { isSelected ->
                    addSelection(isSelected, filters.value.publishers, Publisher.GeorgeLucas)
                    searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
                }
                SorehFilterChip(label = stringResource(R.string.image_comics)) { isSelected ->
                    addSelection(isSelected, filters.value.publishers, Publisher.Image)
                    searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
                }

            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = stringResource(R.string.power_stats),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp)
            )

            val rangeSliderModifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            SorehRangeSlider(
                modifier = rangeSliderModifier,
                label = stringResource(id = R.string.intelligence)
            ) {
                filters.value.intelligence = it.start..it.endInclusive
                searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
            }
            SorehRangeSlider(
                modifier = rangeSliderModifier,
                label = stringResource(id = R.string.strength)
            ) {
                filters.value.strength = it.start..it.endInclusive
                searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
            }
            SorehRangeSlider(
                modifier = rangeSliderModifier,
                label = stringResource(id = R.string.speed)
            ) {
                filters.value.speed = it.start..it.endInclusive
                searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
            }
            SorehRangeSlider(
                modifier = rangeSliderModifier,
                label = stringResource(id = R.string.durability)
            ) {
                filters.value.durability = it.start..it.endInclusive
                searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
            }
            SorehRangeSlider(
                modifier = rangeSliderModifier,
                label = stringResource(id = R.string.power)
            ) {
                filters.value.power = it.start..it.endInclusive
                searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
            }
            SorehRangeSlider(
                modifier = rangeSliderModifier,
                label = stringResource(id = R.string.combat)
            ) {
                filters.value.combat = it.start..it.endInclusive
                searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = stringResource(R.string.gender),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp)
            )

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
            ) {

                SorehFilterChip(label = stringResource(R.string.male)) {
                    addSelection(it, filters.value.genders, Gender.Male)
                    searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
                }
                SorehFilterChip(label = stringResource(R.string.female)) {
                    addSelection(it, filters.value.genders, Gender.Female)
                    searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
                }
                SorehFilterChip(label = stringResource(R.string.agender)) {
                    addSelection(it, filters.value.genders, Gender.Agender)
                    searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
                }

            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                stringResource(R.string.alignment),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
            ) {

                SorehFilterChip(label = stringResource(R.string.light_side)) {
                    addSelection(it, filters.value.alignments, Light)
                    searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
                }
                SorehFilterChip(label = stringResource(R.string.dark_side)) {
                    addSelection(it, filters.value.alignments, Dark)
                    searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
                }
                SorehFilterChip(label = stringResource(R.string.neutral)) {
                    addSelection(it, filters.value.alignments, Neutral)
                    searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
                }

            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                stringResource(R.string.race),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 8.dp, top = 8.dp)
            )

            FlowRow(
                horizontalArrangement = Arrangement.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 8.dp)
                    .fillMaxWidth()
            ) {

                SorehFilterChip(label = stringResource(R.string.human)) {
                    addSelection(it, filters.value.races, Race.Human)
                    searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
                }
                SorehFilterChip(label = stringResource(R.string.mutant)) {
                    addSelection(it, filters.value.races, Race.Mutant)
                    searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
                }
                SorehFilterChip(label = stringResource(R.string.god_eternal)) {
                    addSelection(it, filters.value.races, Race.God)
                    searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
                }
                SorehFilterChip(label = stringResource(R.string.android)) {
                    addSelection(it, filters.value.races, Race.Android)
                    searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
                }
                SorehFilterChip(label = stringResource(R.string.symbiote)) {
                    addSelection(it, filters.value.races, Race.Symbiote)
                    searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
                }
                SorehFilterChip(label = stringResource(R.string.alien)) {
                    addSelection(it, filters.value.races, Race.Alien)
                    searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
                }
                SorehFilterChip(label = stringResource(R.string.kryptonian)) {
                    addSelection(it, filters.value.races, Race.Kryptonian)
                    searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
                }
                SorehFilterChip(label = stringResource(R.string.demon)) {
                    addSelection(it, filters.value.races, Race.Demon)
                    searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
                }
                SorehFilterChip(label = stringResource(R.string.alpha)) {
                    addSelection(it, filters.value.races, Race.Alpha)
                    searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
                }
                SorehFilterChip(label = stringResource(R.string.asgardian)) {
                    addSelection(it, filters.value.races, Race.Asgardian)
                    searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
                }
                SorehFilterChip(label = stringResource(R.string.atlantean)) {
                    addSelection(it, filters.value.races, Race.Atlantean)
                    searchViewModel.countTargetCharacters(CharactersQueryBuilder(filters.value).build())
                }

            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))


        }


    }
}

private fun addSelection(
    isSelected: Boolean,
    filters: MutableList<CharacterFieldValues>,
    selectedFilter: CharacterFieldValues,
) {
    if (isSelected) {
        filters.add(selectedFilter)
    } else {
        filters.remove(selectedFilter)
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SorehFilterChip(
    label: String,
    onClick: (isSelected: Boolean) -> Unit = {},

    ) {
    var selected by remember { mutableStateOf(false) }
    FilterChip(
        onClick = {
            selected = !selected
            onClick(selected)
        },
        label = { Text(label) },
        selected = selected,
        border = FilterChipDefaults.filterChipBorder(
            borderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledBorderColor = MaterialTheme.colorScheme.onSecondaryContainer,
            borderWidth = 1.dp
        ),
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.surface,
            selectedContainerColor = MaterialTheme.colorScheme.onSurface,
            labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            selectedLabelColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .padding(2.dp)
            .height(30.dp)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SorehRangeSlider(
    label: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    onValueChangeFinished: (ClosedRange<Int>) -> Unit = {},
) {
    var sliderPosition by remember { mutableStateOf(0f..100f) }
    val startInteractionSource = remember { MutableInteractionSource() }
    val endInteractionSource = remember { MutableInteractionSource() }
    val startThumbAndTrackColors = SliderDefaults.colors(
        thumbColor = MaterialTheme.colorScheme.onSurface,
        activeTrackColor = MaterialTheme.colorScheme.onSurface
    )
    val endThumbColors = SliderDefaults.colors(thumbColor = MaterialTheme.colorScheme.onSurface)
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                style = textStyle,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                text = label
            )
            Text(
                style = textStyle,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                text = "${sliderPosition.start.roundToInt()} - ${sliderPosition.endInclusive.roundToInt()}"
            )
        }
        RangeSlider(
            modifier = Modifier.semantics { contentDescription = "Localized Description" },
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = 0f..100f,
            onValueChangeFinished = {
                onValueChangeFinished(sliderPosition.start.roundToInt()..sliderPosition.endInclusive.roundToInt())
            },
            startInteractionSource = startInteractionSource,
            endInteractionSource = endInteractionSource,
            startThumb = {
                SliderDefaults.Thumb(
                    interactionSource = startInteractionSource,
                    colors = startThumbAndTrackColors
                )
            },
            endThumb = {
                SliderDefaults.Thumb(
                    interactionSource = endInteractionSource,
                    colors = endThumbColors
                )
            },
            track = { sliderPositions ->
                SliderDefaults.Track(
                    colors = startThumbAndTrackColors,
                    sliderPositions = sliderPositions
                )
            }
        )
    }
}
