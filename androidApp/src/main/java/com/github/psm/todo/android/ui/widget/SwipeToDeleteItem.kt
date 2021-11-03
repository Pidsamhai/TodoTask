package com.github.psm.todo.android.ui.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.psm.todo.android.ui.theme.IconColor
import com.github.psm.todo.android.ui.theme.TodoApplicationTheme
import com.github.psm.todo.android.utils.toCPx
import com.github.psm.todo.android.utils.toIntOffset

sealed class SwipeState {
    object Open : SwipeState()
    object Close : SwipeState()
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SwipeToDeleteItem(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit,
    hint: @Composable () -> Unit,
    actionWidth: Dp,
    swipeState: SwipeState = SwipeState.Close,
    onSwipe: (SwipeState) -> Unit = { }
) {
    val closeOffset = Offset.Zero
    val openOffset = Offset.Zero.copy(x = -actionWidth.toCPx())

    var contentOffset by remember {
        mutableStateOf(Offset.Zero)
    }

    val contentAnimOffset by animateOffsetAsState(
        targetValue = contentOffset,
        animationSpec = spring(),
        finishedListener = {
            val state = if (it == Offset.Zero) SwipeState.Close
            else SwipeState.Open
            onSwipe(state)
        }
    )

    val animateToOpenState: () -> Unit = {
        contentOffset = openOffset
    }

    val animateToCloseState: () -> Unit = {
        contentOffset = closeOffset
    }

    LaunchedEffect(key1 = swipeState) {
        when (swipeState) {
            SwipeState.Close -> animateToCloseState()
            SwipeState.Open -> animateToOpenState()
        }
    }

    BoxWithConstraints(
        modifier = modifier
            .clipToBounds()
    ) {

        Row(
            modifier = Modifier
                .matchParentSize(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
            verticalAlignment = Alignment.CenterVertically
        ) {
            actions()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset { contentAnimOffset.toIntOffset() }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            if (dragAmount < 0) animateToOpenState()
                        }
                    )
                }
        ) {
            content()
            Box(
                modifier = Modifier
                    .padding(start = actionWidth)
                    .matchParentSize()
            ) {
                AnimatedVisibility(
                    visible = contentOffset != Offset.Zero,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    hint()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SwipeToDeleteItemPrev(
    content: (@Composable () -> Unit)? = null
) {

    var state: SwipeState by remember {
        mutableStateOf(SwipeState.Close)
    }

    val stateText: String = when (state) {
        is SwipeState.Close -> "Close"
        is SwipeState.Open -> "Open"
    }

    TodoApplicationTheme {
        SwipeToDeleteItem(
            swipeState = state,
            onSwipe = {
                state = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            content = {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = Color.Blue
                ) {
                    if (content == null) {
                        Text(
                            modifier = Modifier
                                .padding(
                                    vertical = 16.dp,
                                    horizontal = 8.dp
                                ),
                            text = stateText,
                            color = Color.White
                        )
                    } else {
                        content()
                    }
                }
            },
            actions = {
                UndoButton {
                    state = SwipeState.Close
                }
            },
            hint = {
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(Color.White),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(text = "Hint")
                }
            },
            actionWidth = 72.dp
        )
    }
}