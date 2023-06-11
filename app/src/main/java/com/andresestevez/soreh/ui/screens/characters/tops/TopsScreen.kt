package com.andresestevez.soreh.ui.screens.characters.tops

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.andresestevez.soreh.R
import com.andresestevez.soreh.ui.SorehAppState
import com.andresestevez.soreh.ui.screens.common.SquareIndicator
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopsScreen(
    viewModel: TopsViewModel = hiltViewModel(),
    appState: SorehAppState,
    onClick: (Int) -> Unit,
) {

    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf(
        ImageVector.vectorResource(id = R.drawable.dc_logo),
        ImageVector.vectorResource(id = R.drawable.marvel),
        ImageVector.vectorResource(id = R.drawable.image_comics),
        ImageVector.vectorResource(id = R.drawable.dark_horse_comics),
        ImageVector.vectorResource(id = R.drawable.lucasarts),
        ImageVector.vectorResource(id = R.drawable.nbc),
    )
    val pagerState = rememberPagerState(
        initialPage = 2,
        initialPageOffsetFraction = 0.5f
    ) {
        tabs.size
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = appState.scaffoldPadding.value.calculateTopPadding())
    ) {

        TabRow(
            modifier = Modifier.height(32.dp),
            selectedTabIndex = pagerState.currentPage,
            indicator = {},
            divider = {}
        ) {
            tabs.forEachIndexed { index, imageVector ->

                val modifier =
                    if (index == 0 || index == 5) Modifier.height(30.dp) else Modifier.width(50.dp)

                Tab(
                    selected = index == pagerState.currentPage,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = imageVector,
                            contentDescription = null,
                            modifier = modifier
                        )
                    }
                )
            }


        }

        SquareIndicator(state = pagerState, numberOfPages = tabs.size)

        SorehTopsPager(
            pagerState,
            onClick,
            appState,
            viewModel,
        )
    }


}



