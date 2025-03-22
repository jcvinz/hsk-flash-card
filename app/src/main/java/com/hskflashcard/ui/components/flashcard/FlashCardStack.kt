package com.hskflashcard.ui.components.flashcard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.hskflashcard.data.source.local.room.HSKWord

@Composable
fun FlashCardStack(
    modifier: Modifier = Modifier,
    words: List<HSKWord>,
    examples: String,
    isLoading: Boolean,
    onAiButtonClicked: (String) -> Unit,
    onBookmarkClicked: (Boolean, Int) -> Unit,
    onSwiped: (FlashCardDirection) -> Unit
) {

    val visibleWords = 3
    val activeCards =
        remember {
            mutableListOf<HSKWord>().apply {
                addAll(words.take(visibleWords))
            }
        }
    var nextCardIndex by remember { mutableIntStateOf(activeCards.size) }

    Box(modifier = modifier.fillMaxSize()) {
        activeCards.reversed().forEachIndexed { index, word ->
            val topCard = index == 0

            FlashCard(
                word = words[index],
                examples = examples,
                isLoading = isLoading,
                enableGestures = topCard,
                onAiButtonClicked = onAiButtonClicked,
                onBookmarkClicked = onBookmarkClicked,
                onSwiped = {
                    onSwiped
                    if (nextCardIndex < words.size) {
                        activeCards.add(0, words[nextCardIndex])
                        nextCardIndex++
                    }
                }
            )
        }
    }

}