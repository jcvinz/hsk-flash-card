package com.hskflashcard.ui.screens.homecontainer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hskflashcard.ui.components.BottomNavBar
import com.hskflashcard.ui.components.flashcard.FlashCard
import com.hskflashcard.ui.screens.HomeScreenType
import com.hskflashcard.ui.screens.home.HomeScreen

@Composable
fun HomeScreenContainer(
    viewModel: HomeContainerViewModel = hiltViewModel()
) {
    val homeNavController = rememberNavController()
    HomeScreenContainerContent(navController = homeNavController)
}

@Composable
fun HomeScreenContainerContent(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomNavBar(navController = navController)
        }) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = HomeScreenType.Home
        ) {
            composable<HomeScreenType.Home> {
                HomeScreen()
            }
            composable<HomeScreenType.Bookmark> {
                Box(modifier = Modifier.fillMaxSize()) {

//                    Text("Bookmark Screen", modifier = Modifier.align(Alignment.Center))

//                    DraftFlashCard(
//                        modifier = Modifier.align(Alignment.Center),
//                        hanzi = "爱",
//                        pinyin = "ài",
//                        meaning = "love",
//                        examples = "Your example text here",
//                        onSwipeLeft = { Log.d("CheckOffset", "Swipe Left") },
//                        onSwipeRight = { Log.d("CheckOffset", "Swipe Right") },
//                        onUndo = { /* Handle undo action */ }
//                    )

                    FlashCard(
                        modifier = Modifier.align(Alignment.Center),
                        hanzi = "爱",
                        pinyin = "ài",
                        meaning = "love",
                        examples = "Examples :\n" +
                                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vel ipsum in metus sagittis mollis. Nunc feugiat urna non maximus accumsan. Ut tempus odio in pellentesque eleifend. Aliquam mauris ipsum, posuere mattis sollicitudin in, volutpat ac nisi. Phasellus luctus sem elit, eget sodales justo fringilla in. Nullam quis metus eu velit cursus ultrices sed sed nisl. In accumsan quis mi sed varius. Curabitur justo urna, imperdiet nec faucibus sed, hendrerit in mauris. Nam nec ligula diam. Nullam et lectus in velit ultricies consequat. Phasellus posuere velit tortor. Praesent diam dui, lacinia sit amet enim et, feugiat dictum nunc. Phasellus tristique enim tristique odio condimentum, eget maximus risus pellentesque. Donec convallis est in lacus convallis, sed placerat nibh ornare. Donec leo velit, molestie quis dui eget, mattis finibus diam. Suspendisse consectetur tortor ante, sed lobortis libero malesuada et." +
                                "\n\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc vel ipsum in metus sagittis mollis. Nunc feugiat urna non maximus accumsan. Ut tempus odio in pellentesque eleifend. Aliquam mauris ipsum, posuere mattis sollicitudin in, volutpat ac nisi. Phasellus luctus sem elit, eget sodales justo fringilla in. Nullam quis metus eu velit cursus ultrices sed sed nisl. In accumsan quis mi sed varius. Curabitur justo urna, imperdiet nec faucibus sed, hendrerit in mauris. Nam nec ligula diam. Nullam et lectus in velit ultricies consequat. Phasellus posuere velit tortor. Praesent diam dui, lacinia sit amet enim et, feugiat dictum nunc. Phasellus tristique enim tristique odio condimentum, eget maximus risus pellentesque. Donec convallis est in lacus convallis, sed placerat nibh ornare. Donec leo velit, molestie quis dui eget, mattis finibus diam. Suspendisse consectetur tortor ante, sed lobortis libero malesuada et.",
                        isLoading = false,
                        isBookmarked = true,
                        enableGestures = true,
                        onSwiped = { }
                    )

//                    FlashCard(
//                        modifier = Modifier.align(Alignment.Center),
//                        hanzi = "爱",
//                        pinyin = "ài",
//                        meaning = "love",
//                        examples = "",
//                        isLoading = false,
//                        isBookmarked = true
//                    )
                }
            }
        }
    }
}