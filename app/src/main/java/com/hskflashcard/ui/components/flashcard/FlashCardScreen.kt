package com.hskflashcard.ui.components.flashcard

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hskflashcard.data.source.local.room.HSKWord
import com.hskflashcard.ui.theme.HSKFlashCardTheme

@Composable
fun FlashCardScreen(
    hskLevel: String,
    viewModel: FlashCardViewModel = hiltViewModel()
) {

    FlashCardScreenContent(
        hskLevel = hskLevel,
        learnedWords = viewModel.learnedWords.collectAsState().value,
        totalWords = viewModel.totalWords.collectAsState().value,
        activeWords = viewModel.activeWords.collectAsState().value,
        examples = viewModel.examples.collectAsState().value,
        isLoading = viewModel.isLoading.collectAsState().value,
        onAiButtonClicked = viewModel::onAiButtonClicked,
        onBookmarkClicked = { _, _ -> },
        onSwiped = viewModel::onSwiped,
    )

}

@Composable
fun FlashCardScreenContent(
    modifier: Modifier = Modifier,
    hskLevel: String,
    learnedWords: Int,
    totalWords: Int,
    activeWords: List<HSKWord>,
    examples: String,
    isLoading: Boolean,
    onAiButtonClicked: (String) -> Unit,
    onBookmarkClicked: (Boolean, Int) -> Unit,
    onSwiped: (FlashCardDirection, Int) -> Unit
) {
    val progress by animateFloatAsState(learnedWords.toFloat() / totalWords, label = "")

    Scaffold(
        modifier = modifier
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = hskLevel,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(16.dp))
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    progress = { progress }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "$learnedWords reviewed")
                    Text(text = "$totalWords cards")
                }
                Spacer(modifier = Modifier.height(64.dp))
                FlashCardStack(
                    activeWords = activeWords,
                    examples = examples,
                    isLoading = isLoading,
                    onAiButtonClicked = onAiButtonClicked,
                    onBookmarkClicked = onBookmarkClicked,
                    onSwiped = onSwiped
                )
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
//        FlashCardScreenContent(
//            hskLevel = "HSK1"
//        )
    }
}