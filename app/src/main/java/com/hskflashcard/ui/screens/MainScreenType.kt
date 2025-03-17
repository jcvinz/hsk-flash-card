package com.hskflashcard.ui.screens

import kotlinx.serialization.Serializable

@Serializable
sealed class MainScreenType {
    @Serializable
    data object SignIn: MainScreenType()
    @Serializable
    data object Home: MainScreenType()
}