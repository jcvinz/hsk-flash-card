package com.hskflashcard.ui.components.flashcard

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.snap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hskflashcard.R
import kotlin.math.abs

@Composable
fun FlashCard(
    modifier: Modifier = Modifier,
    hanzi: String,
    pinyin: String,
    meaning: String,
    examples: String,
    isLoading: Boolean,
    isBookmarked: Boolean,
    enableGestures: Boolean,
    onSwiped: (FlashCardDirection) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val thresholdPercentage = 0.4f
    val thresholdInDp = screenWidth * thresholdPercentage
    val thresholdInPx = with(LocalDensity.current) { thresholdInDp.dp.toPx() }
    val invisibleThreshold = with(LocalDensity.current) { screenWidth.dp.toPx() }

    var isFlipped by remember { mutableStateOf(false) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var opacity by remember { mutableFloatStateOf(1f) }

    val animatedOpacity by animateFloatAsState(
        targetValue = opacity,
        animationSpec = snap()
    )

    val animatedOffset by animateIntOffsetAsState(
        targetValue = IntOffset(offsetX.toInt(), 0),
        animationSpec = snap()
    )

    val scrollState = rememberScrollState()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
            .offset { animatedOffset }
            .alpha(animatedOpacity)
            .then(
                if (enableGestures) {
                    Modifier.pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onDragEnd = {
                                when {
                                    offsetX < -thresholdInPx -> {
                                        Log.d("FlashCardOffset", "Swipe Left")
                                        Log.d("FlashCardOffset", "offsetX: $offsetX")
                                        offsetX = -invisibleThreshold
                                        onSwiped(FlashCardDirection.LEFT)
                                    }

                                    offsetX > thresholdInPx -> {
                                        Log.d("FlashCardOffset", "Swipe Right")
                                        Log.d("FlashCardOffset", "offsetX: $offsetX")
                                        offsetX = invisibleThreshold
                                        onSwiped(FlashCardDirection.RIGHT)
                                    }

                                    else -> {
                                        offsetX = 0f
                                        opacity = 1f
                                    }
                                }
                            },
                            onDragCancel = {
                                offsetX = 0f
                                opacity = 1f
                            },
                            onHorizontalDrag = { pointerInputChange, amount ->
                                offsetX += amount
                                val alpha = 1f - (abs(offsetX) / 500f)
                                opacity = alpha.coerceIn(0f, 1f)
                            }
                        )
                    }
                } else {
                    Modifier
                }
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
                .height(if (examples.isNotEmpty()) 644.dp else 516.dp)
                .noRippleClickable {
                    isFlipped = !isFlipped
                },
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                modifier = Modifier.matchParentSize(),
                targetState = isFlipped,
                label = "card_content"
            ) { flipped ->
                if (!flipped) {
                    Box(
                        modifier = Modifier.matchParentSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = hanzi,
                            style = MaterialTheme.typography.displayLarge.copy(fontSize = 96.sp)
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .matchParentSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (examples.isEmpty()) Spacer(modifier = Modifier.weight(1f))
                        else Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = pinyin,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = hanzi,
                            style = MaterialTheme.typography.displayLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = meaning,
                            style = MaterialTheme.typography.titleLarge
                        )
                        if (examples.isEmpty()) {
                            Spacer(modifier = Modifier.weight(1f))
                            AnimatedContent(
                                targetState = isLoading,
                                label = "button_content"
                            ) { loading ->
                                if (!loading) {
                                    Button(
                                        onClick = { }
                                    ) {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(R.drawable.ic_ai_companion),
                                            contentDescription = null
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(text = "Give me examples")
                                    }
                                } else {
                                    CircularProgressIndicator()
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        } else {
                            Spacer(modifier = Modifier.height(16.dp))
                            Box(
                                modifier = Modifier
                                    .heightIn(max = 356.dp)
                                    .fillMaxWidth()
                                    .verticalScroll(scrollState)
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = examples,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            IconButton(
                                onClick = { }
                            ) {
                                AnimatedContent(
                                    targetState = isBookmarked
                                ) { bookmarked ->
                                    if (bookmarked) {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(R.drawable.ic_round_bookmark),
                                            null
                                        )
                                    } else {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(R.drawable.ic_round_bookmark_border),
                                            null
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}