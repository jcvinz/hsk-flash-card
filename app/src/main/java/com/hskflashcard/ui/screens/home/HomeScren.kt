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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hskflashcard.R
import com.hskflashcard.ui.components.DailyGoalsCard
import com.hskflashcard.ui.theme.HSKFlashCardTheme

@Composable
fun HomeScreen() {
    HomeScreenContent(name = "Jonathan Calvin")
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    name: String
) {
    val hskLevelList = listOf("HSK 1", "HSK 2", "HSK 3", "HSK 4", "HSK 5", "HSK 6", "HSK 7-9")

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
        DailyGoalsCard(
            modifier = Modifier.padding(horizontal = 24.dp),
            learnedWords = 8
        )
        Spacer(modifier = Modifier.height(32.dp))
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
            name = "Jonathan Calvin"
        )
    }
}