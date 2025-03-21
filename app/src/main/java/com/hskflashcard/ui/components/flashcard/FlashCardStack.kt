package com.hskflashcard.ui.components.flashcard

import androidx.collection.mutableIntListOf
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.hskflashcard.data.source.local.room.HSKWord

@Composable
fun FlashCardStack(
    modifier: Modifier = Modifier,
    words: List<HSKWord>
) {

    val visibleWords = 3
    val processedIndex = remember { mutableIntListOf() }
    val activeCards =
        remember { mutableListOf<HSKWord>().apply { addAll(words.take(visibleWords)) } }
    val nextCardIndex = remember { mutableIntStateOf(visibleWords) }

    Box(modifier = modifier.fillMaxSize()) {
        activeCards.reversed().forEachIndexed { index, word ->
            val topCard = index == 0

        }
    }

}