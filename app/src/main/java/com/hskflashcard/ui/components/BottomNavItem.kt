package com.hskflashcard.ui.components

import androidx.compose.ui.graphics.vector.ImageVector
import com.hskflashcard.ui.screens.HomeScreenType

data class BottomNavItem(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: HomeScreenType
)
