package com.hskflashcard.ui.screens.homecontainer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hskflashcard.ui.components.BottomNavBar
import com.hskflashcard.ui.screens.HomeScreenType
import com.hskflashcard.ui.screens.home.HomeScreen
import com.hskflashcard.ui.screens.profile.ProfileScreen

@Composable
fun HomeScreenContainer(
    navController: NavHostController,
    viewModel: HomeContainerViewModel = hiltViewModel()
) {
    val homeNavController = rememberNavController()
    HomeScreenContainerContent(
        mainNavController = navController,
        homeNavController = homeNavController
    )
}

@Composable
fun HomeScreenContainerContent(
    modifier: Modifier = Modifier,
    mainNavController: NavHostController,
    homeNavController: NavHostController
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomNavBar(navController = homeNavController)
        }) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = homeNavController,
            startDestination = HomeScreenType.Home
        ) {
            composable<HomeScreenType.Home> {
                HomeScreen(mainNavController)
            }
            composable<HomeScreenType.Bookmark> {
                Box(modifier = Modifier.fillMaxSize())
            }
            composable<HomeScreenType.Profile> {
                ProfileScreen()
            }
        }
    }
}