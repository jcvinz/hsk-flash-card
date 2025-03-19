package com.hskflashcard.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hskflashcard.R
import com.hskflashcard.ui.screens.HomeScreenType

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route.toString()

    val items = listOf(
        BottomNavItem(
            label = "Home",
            selectedIcon = ImageVector.vectorResource(R.drawable.ic_round_home),
            unselectedIcon = ImageVector.vectorResource(R.drawable.ic_outline_home),
            route = HomeScreenType.Home
        ),
        BottomNavItem(
            label = "Bookmark",
            selectedIcon = ImageVector.vectorResource(R.drawable.ic_round_bookmark),
            unselectedIcon = ImageVector.vectorResource(R.drawable.ic_round_bookmark_border),
            route = HomeScreenType.Bookmark
        )
    )

    NavigationBar(
        modifier = modifier
    ) {
        items.forEach {
            NavigationBarItem(
                selected = currentRoute.endsWith(it.route.toString()),
                icon = {
                    if (currentRoute.endsWith(it.route.toString())) Icon(
                        it.selectedIcon,
                        contentDescription = it.label
                    ) else Icon(
                        it.unselectedIcon, contentDescription = it.label
                    )
                },
                label = {
                    Text(text = it.label)
                },
                onClick = {
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }
}