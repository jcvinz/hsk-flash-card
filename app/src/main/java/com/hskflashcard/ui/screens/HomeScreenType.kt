package com.hskflashcard.ui.screens

import kotlinx.serialization.Serializable

@Serializable
sealed class HomeScreenType {
    @Serializable
    data object Home : HomeScreenType()
    @Serializable
    data object Bookmark : HomeScreenType()
    @Serializable
    data object Profile : HomeScreenType()
}