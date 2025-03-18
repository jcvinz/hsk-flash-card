package com.hskflashcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.hskflashcard.ui.screens.MainScreenType
import com.hskflashcard.ui.screens.signin.SignInScreen
import com.hskflashcard.ui.theme.HSKFlashCardTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { false }

        val currentUser = firebaseAuth.currentUser

        enableEdgeToEdge()
        setContent {
            HSKFlashCardTheme {
                val navController = rememberNavController()
                NavHost(
                    navController,
                    startDestination = if (currentUser == null) MainScreenType.SignIn else MainScreenType.Home
                ) {
                    composable<MainScreenType.SignIn> {
                        SignInScreen(navController)
                    }
                    composable<MainScreenType.Home> {

                    }
                }
            }
        }
    }
}