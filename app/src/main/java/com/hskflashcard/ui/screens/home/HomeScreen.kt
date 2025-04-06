package com.hskflashcard.ui.screens.home

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.hskflashcard.R
import com.hskflashcard.ui.screens.MainScreenType
import com.hskflashcard.ui.theme.HSKFlashCardTheme

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    HomeScreenContent(
        name = "Jonathan Calvin",
        progress = viewModel.progress.collectAsState().value
    ) {
        navController.navigate(MainScreenType.FlashCard(hskLevel = it)) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    }
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    name: String,
    progress: Pair<Int, Int>,
    onLevelClicked: (String) -> Unit
) {
    val hskLevelList = listOf("HSK 1", "HSK 2", "HSK 3", "HSK 4", "HSK 5", "HSK 6")

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 24.dp, end = 24.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Hi, $name",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Let's learn new word everyday",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Icon(
                modifier = Modifier.clickable { },
                imageVector = ImageVector.vectorResource(R.drawable.ic_round_more_vert),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

//        AllProgressCard(
//            modifier = Modifier.padding(horizontal = 24.dp),
//            learnedWords = progress.first,
//            totalWords = progress.second
//        )
//        Spacer(modifier = Modifier.height(32.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = 24.dp, start = 24.dp, end = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp), // Space between columns
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(hskLevelList.size, key = {
                hskLevelList[it]
            }) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 180.dp)
                        .clickable {
                            onLevelClicked(hskLevelList[it])
                        },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = hskLevelList[it],
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    widthDp = 412, heightDp = 917, showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    widthDp = 412, heightDp = 917, showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun HomeScreenPreview() {
    HSKFlashCardTheme {
        HomeScreenContent(
            name = "Jonathan Calvin",
            progress = Pair(0,0)
        ) { }
    }
}