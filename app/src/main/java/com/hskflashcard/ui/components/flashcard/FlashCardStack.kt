package com.hskflashcard.ui.components.flashcard

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hskflashcard.data.source.local.room.HSKWord

@Composable
fun FlashCardStack(
    modifier: Modifier = Modifier,
    activeWords: List<HSKWord>,
    examples: String,
    isLoading: Boolean,
    onAiButtonClicked: (String) -> Unit,
    onBookmarkClicked: (Boolean, Int) -> Unit,
    onSwiped: (FlashCardDirection, Int) -> Unit
) {

    Log.d("FlashCardStack", "Active Words: $activeWords")

    Box(modifier = modifier) {
        activeWords.reversed().forEachIndexed { index, word ->

            val topIndex = index == activeWords.size - 1

            FlashCard(
                modifier = Modifier.offset(y = (-index * 12).dp),
                word = word,
                examples = examples,
                isLoading = isLoading,
                enableGestures = topIndex,
                onAiButtonClicked = onAiButtonClicked,
                onBookmarkClicked = onBookmarkClicked,
                onSwiped = onSwiped
            )
        }
    }

}