package com.andresestevez.soreh.ui.screens.characters.favorites

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.andresestevez.soreh.ui.SorehAppState
import com.andresestevez.soreh.ui.screens.characters.favorites.detail.FavoritesDetailScreen
import com.andresestevez.soreh.ui.screens.characters.favorites.detail.FavoritesDetailViewModel
import com.mxalbert.sharedelements.DelayExit
import com.mxalbert.sharedelements.FadeMode
import com.mxalbert.sharedelements.MaterialArcMotionFactory
import com.mxalbert.sharedelements.MaterialContainerTransformSpec
import com.mxalbert.sharedelements.ProgressThresholds
import com.mxalbert.sharedelements.SharedElementsRoot
import com.mxalbert.sharedelements.SharedElementsRootScope
import com.mxalbert.sharedelements.SharedElementsTransitionSpec

const val FavoritesScreen = "favoritesScreen"
 const val FavoritesDetailScreen = "favoritesDetailScreen"

private var selectedCharacter: Int by mutableIntStateOf(-1)
private var previousSelectedUser: Int = -1

@Composable
fun FavoritesRoot(
    viewModel : FavoritesViewModel = hiltViewModel(),
    detailViewModel : FavoritesDetailViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    appState: SorehAppState,
    onClick: (Int) -> Unit = {},
) {
    SharedElementsRoot {
        val user = selectedCharacter
        val gridState = rememberLazyGridState()
        val state by viewModel.state.collectAsState()

        BackHandler(enabled = user >= 0) {
            appState.forceTopAppBar = true
            appState.forceBottomNavigationBar = true
            changeCharacter(-1)
        }

        DelayExit(visible = user < 0) {
            appState.forceTopAppBar = false
            appState.forceBottomNavigationBar = false
            FavoritesScreen(
                viewModel,
                paddingValues = paddingValues,
                onClick = { detailViewModel.selectCharacter(it) },
                lazyGridState = gridState
            )
        }

        DelayExit(visible = user >= 0) {
            detailViewModel.selectCharacter(selectedCharacter)
            appState.forceTopAppBar = false
            appState.forceBottomNavigationBar = false
            FavoritesDetailScreen(detailViewModel, paddingValues)
        }
    }




}

fun SharedElementsRootScope.changeCharacter(id: Int) {
    val currentUser = selectedCharacter
    if (currentUser != id) {
        val targetUser = if (id >= 0) id else currentUser
        if (targetUser >= 0) {
                prepareTransition(id)

        }
        previousSelectedUser = selectedCharacter
        selectedCharacter = id
    }
}



private const val TransitionDurationMillis = 1000

val FadeOutTransitionSpec = MaterialContainerTransformSpec(
    durationMillis = TransitionDurationMillis,
    fadeMode = FadeMode.Out
)
val CrossFadeTransitionSpec = SharedElementsTransitionSpec(
    durationMillis = TransitionDurationMillis,
    fadeMode = FadeMode.Cross,
    fadeProgressThresholds = ProgressThresholds(0.10f, 0.40f)
)
val MaterialFadeInTransitionSpec = MaterialContainerTransformSpec(
    pathMotionFactory = MaterialArcMotionFactory,
    durationMillis = TransitionDurationMillis,
    fadeMode = FadeMode.In
)
val MaterialFadeOutTransitionSpec = MaterialContainerTransformSpec(
    pathMotionFactory = MaterialArcMotionFactory,
    durationMillis = TransitionDurationMillis,
    fadeMode = FadeMode.Out
)