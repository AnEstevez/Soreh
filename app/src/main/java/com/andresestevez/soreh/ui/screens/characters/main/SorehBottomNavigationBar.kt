package com.andresestevez.soreh.ui.screens.characters.main

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MilitaryTech
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.andresestevez.soreh.ui.navigation.NavCommand

@Composable
fun SorehBottomNavigationBar(
    navItems: List<NavItem>,
    currentRoute: String,
    onClick: (String) -> Unit,
) {

    NavigationBar(
        modifier = Modifier
            .height(120.dp)
            .padding(15.dp)
            .navigationBarsPadding()
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(15),
                spotColor = MaterialTheme.colorScheme.onSurface
            )
            .clip(RoundedCornerShape(15)),
        tonalElevation = 0.dp,
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        navItems.forEach {
            NavigationBarItem(
                selected = currentRoute.contains(it.navCommand.destination),
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.onSurface,
                    selectedIconColor = MaterialTheme.colorScheme.surface,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant

                ),
                icon = { Icon(imageVector = it.icon, contentDescription = it.name) },
                onClick = { onClick(it.navCommand.destination) },
            )
        }
    }
}

enum class NavItem(
    val navCommand: NavCommand,
    val icon: ImageVector,
) {
    HOME(NavCommand.Home, Icons.Outlined.Home),
    SEARCH(NavCommand.Search, Icons.Outlined.Search),
    TOPS(NavCommand.Tops, Icons.Outlined.MilitaryTech),
    FAVORITES(NavCommand.Favorites, Icons.Outlined.FavoriteBorder),
}