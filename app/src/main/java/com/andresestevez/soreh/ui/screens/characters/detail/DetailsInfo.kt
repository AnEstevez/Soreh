package com.andresestevez.soreh.ui.screens.characters.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AltRoute
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.FamilyRestroom
import androidx.compose.material.icons.outlined.Female
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Height
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Male
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.andresestevez.soreh.R


@Composable
fun DetailsInfo(
    showDetails: Boolean,
    state: CharacterDetailViewModel.UiState,
    iconsColor: Color = MaterialTheme.colorScheme.onSurface,
    titlesColor: Color = MaterialTheme.colorScheme.onSurface,
    infoColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    AnimatedVisibility(
        visible = showDetails,
        enter = expandVertically(),
        exit = shrinkVertically(),
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.Start

        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp, top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(IntrinsicSize.Max)
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.race),
                            contentDescription = "",
                            tint = iconsColor
                        )

                        Text(
                            textAlign = TextAlign.Center,
                            text = state.data?.character?.race.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = infoColor,
                        )

                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(IntrinsicSize.Max)
                    ) {

                        when (state.data?.character?.gender) {
                            "Male" -> Icon(
                                imageVector = Icons.Outlined.Male,
                                contentDescription = "",
                                tint = iconsColor
                            )

                            "Female" -> Icon(
                                imageVector = Icons.Outlined.Female,
                                contentDescription = "",
                                tint = iconsColor
                            )

                            else -> Icon(
                                painter = painterResource(id = R.drawable.agender),
                                contentDescription = "",
                                tint = iconsColor
                            )
                        }

                        Text(
                            textAlign = TextAlign.Center,
                            text = state.data?.character?.gender
                                ?: stringResource(id = R.string.unknownValue),
                            color = infoColor,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(IntrinsicSize.Max)
                    ) {

                        Icon(
                            imageVector = Icons.Outlined.Height,
                            contentDescription = "",
                            tint = iconsColor
                        )

                        Text(
                            textAlign = TextAlign.Center,
                            text = if (state.data?.character?.height.toString() == "0") "- cm"
                            else state.data?.character?.height.toString() + " cm",
                            color = infoColor,
                            style = MaterialTheme.typography.bodyMedium
                        )


                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(IntrinsicSize.Max)
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.weight),
                            contentDescription = "",
                            tint = iconsColor
                        )

                        Text(
                            textAlign = TextAlign.Center,
                            text = if (state.data?.character?.weight.toString() == "0") "- kg"
                            else state.data?.character?.weight.toString() + " kg",
                            color = infoColor,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(IntrinsicSize.Max)
                    ) {

                        Icon(
                            imageVector = Icons.Outlined.AltRoute,
                            contentDescription = "",
                            tint = iconsColor
                        )

                        Text(
                            textAlign = TextAlign.Center,
                            text = state.data?.character?.alignment
                                ?: stringResource(id = R.string.unknownValue),
                            color = infoColor,
                            style = MaterialTheme.typography.bodyMedium
                        )

                    }

                }

            }

            Column(modifier = Modifier.padding(top = 5.dp, bottom = 10.dp)) {
                Row {
                    Text(
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.identity),
                        color = titlesColor,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Icon(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        imageVector = Icons.Outlined.Badge,
                        contentDescription = "",
                        tint = iconsColor
                    )

                }
                Text(
                    textAlign = TextAlign.Start,
                    text = state.data?.character?.fullName
                        ?: stringResource(id = R.string.unknownValue),
                    color = infoColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Column(modifier = Modifier.padding(vertical = 10.dp)) {
                Row {
                    Text(
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.publisher_universe),
                        color = titlesColor,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Icon(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        imageVector = Icons.Outlined.AutoStories,
                        contentDescription = "",
                        tint = iconsColor
                    )

                }
                Text(
                    textAlign = TextAlign.Center,
                    text = state.data?.character?.publisher
                        ?: stringResource(id = R.string.unknownValue),
                    color = infoColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Column(modifier = Modifier.padding(vertical = 10.dp)) {
                Row {
                    Text(
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.first_appearance),
                        color = titlesColor,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Icon(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        imageVector = Icons.Outlined.History,
                        contentDescription = "",
                        tint = iconsColor
                    )
                }
                Text(
                    textAlign = TextAlign.Start,
                    text = state.data?.character?.firstAppearance
                        ?: stringResource(id = R.string.unknownValue),
                    color = infoColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Column(modifier = Modifier.padding(vertical = 10.dp)) {
                Row {
                    Text(
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.relatives),
                        color = titlesColor,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Icon(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        imageVector = Icons.Outlined.FamilyRestroom,
                        contentDescription = "",
                        tint = iconsColor
                    )
                }
                Text(
                    textAlign = TextAlign.Start,
                    text = state.data?.character?.relatives
                        ?: stringResource(id = R.string.unknownValue),
                    color = infoColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Column(modifier = Modifier.padding(vertical = 10.dp)) {

                Row {
                    Text(
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.affiliations),
                        color = titlesColor,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Icon(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        imageVector = Icons.Outlined.Groups,
                        contentDescription = "",
                        tint = iconsColor
                    )

                }
                Text(
                    textAlign = TextAlign.Start,
                    text = state.data?.character?.groupAffiliation
                        ?: stringResource(id = R.string.unknownValue),
                    color = infoColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Divider(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(top = 10.dp)
                    .fillMaxWidth()
            )

        }
    }
}