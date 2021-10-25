package com.github.psm.todo.android

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.github.psm.todo.android.ui.theme.TodoApplicationTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.collect
import kotlin.math.roundToInt

internal data class State(val drawer: Offset, val content: Offset)

internal interface DrawerStateController {
    fun openStateOffset(): State
    fun closeStateOffset(): State
}

class DrawerState(
    initialValue: DrawerStateValue = DrawerStateValue.Close
) {
    private var currentState = mutableStateOf(initialValue)

    val state: DrawerStateValue
        get() = currentState.value

    val isOpen: Boolean
        get() = currentState.value == DrawerStateValue.Open

    val isClose: Boolean
        get() = currentState.value == DrawerStateValue.Close

    fun open() {
        currentState.value = DrawerStateValue.Open
    }

    fun close() {
        currentState.value = DrawerStateValue.Close
    }

    fun toggle() {
        if (currentState.value == DrawerStateValue.Close) {
            currentState.value = DrawerStateValue.Open
        } else {
            currentState.value = DrawerStateValue.Close
        }
    }

    companion object {
        fun Saver() = Saver<DrawerState, DrawerStateValue>(
            save = { it.currentState.value },
            restore = { DrawerState(it) }
        )
    }
}

enum class DrawerStateValue {
    Close,
    Open
}

@Composable
fun rememberDrawerState(
    initialValue: DrawerStateValue = DrawerStateValue.Close
): DrawerState {
    return rememberSaveable(saver = DrawerState.Saver()) {
        DrawerState(initialValue = initialValue)
    }
}

@Composable
internal fun drawerStateController(
    contentSize: Dp,
    contentOverlap: Dp
): DrawerStateController {
    val density = LocalDensity.current

    return object : DrawerStateController {
        override fun openStateOffset(): State {
            return State(
                content = Offset(
                    x = with(density) { (contentSize - contentOverlap).toPx() },
                    y = 0f
                ),
                drawer = Offset(
                    x = 0f,
                    y = 0f
                )
            )
        }

        override fun closeStateOffset(): State {
            return State(
                content = Offset(
                    x = 0f,
                    y = 0f
                ),
                drawer = Offset(
                    x = with(density) { -contentSize.toPx() },
                    y = 0f
                )
            )
        }
    }
}

private val DefaultStatusBarColor = Color.White
private val DrawerBackgroundColor = Color(0xFF142C5C)

@Composable
fun CustomDrawerScaffold(
    drawerState: DrawerState,
    content: @Composable () -> Unit = { },
    drawerContent: @Composable () -> Unit = { }
) {
    val systemUiController = rememberSystemUiController()

    var state by remember {
        mutableStateOf(drawerState.isOpen)
    }

    LaunchedEffect(key1 = state) {
        systemUiController.setSystemBarsColor(
            color = if (state) DrawerBackgroundColor else DefaultStatusBarColor,
            darkIcons = !state
        )
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(DrawerBackgroundColor)
    ) {
        val drawerStateController = drawerStateController(
            contentSize = maxWidth,
            contentOverlap = 100.dp
        )

        var contentOffset by remember {
            mutableStateOf(drawerStateController.closeStateOffset().content)
        }

        var drawerOffset by remember {
            mutableStateOf(drawerStateController.closeStateOffset().drawer)
        }

        val contentOffsetAnim by animateOffsetAsState(
            targetValue = contentOffset,
            animationSpec = tween(
                durationMillis = 600,
                easing = FastOutSlowInEasing
            )
        )

        var contentPadding by remember {
            mutableStateOf(0.dp)
        }

        val animContentPadding by animateDpAsState(
            targetValue = contentPadding,
            animationSpec = tween(
                durationMillis = 600,
                easing = FastOutSlowInEasing
            )
        )

        var contentShapeRadius by remember {
            mutableStateOf(0.dp)
        }

        val animContentShapeRadius by animateDpAsState(
            targetValue = contentShapeRadius,
            animationSpec = tween(
                durationMillis = 600,
                easing = FastOutSlowInEasing
            )
        )

        val drawerOffsetAnim by animateOffsetAsState(
            targetValue = drawerOffset,
            animationSpec = tween(
                durationMillis = 600,
                easing = FastOutSlowInEasing
            )
        )

        val toggleState: () -> Unit = {
            contentOffset = if (!state) {
                drawerStateController.openStateOffset().content
            } else drawerStateController.closeStateOffset().content

            drawerOffset = if (!state) {
                drawerStateController.openStateOffset().drawer
            } else drawerStateController.closeStateOffset().drawer

            contentPadding = if (!state) 32.dp
            else 0.dp

            contentShapeRadius = if (!state) 32.dp
            else 0.dp

            state = !state
        }

        LaunchedEffect(key1 = drawerState) {
            snapshotFlow { drawerState.isOpen }.collect {
                if (it != state) {
                    toggleState()
                }
            }
        }

        Scaffold(
            modifier = Modifier
                .padding(animContentPadding)
                .offset { contentOffsetAnim.toIntOffset() }
                .clip(RoundedCornerShape(animContentShapeRadius)),
            backgroundColor = Color.Red,
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Green),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                content()
            }
        }

        Box(
            modifier = Modifier
                .padding(end = 100.dp)
                .offset { drawerOffsetAnim.toIntOffset() }
        ) {
            drawerContent()
        }
    }
}

private fun Offset.toIntOffset(): IntOffset {
    return IntOffset(
        x = this.x.roundToInt(),
        y = this.y.roundToInt(),
    )
}

@Composable
private fun DrawerContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Drawer Content")
    }
}

@Preview
@Composable
private fun CustomDrawerScaffoldPrev() {
    val state = rememberDrawerState()
    TodoApplicationTheme {
        CustomDrawerScaffold(
            drawerState = state,
            drawerContent = {
                DrawerContent()
            },
            content = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                state.toggle()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Menu,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    },
                    actions = { },
                    title = { Text(text = "App title") }
                )
                Text(
                    modifier = Modifier.clickable {

                    },
                    text = """
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis condimentum, tellus ut tempus finibus, magna enim mollis lacus, et iaculis urna magna vel lectus. Sed maximus lacus dui. Etiam at facilisis ligula. Fusce efficitur justo ac laoreet vehicula. Proin tincidunt faucibus erat, in sodales sem luctus eget. Fusce gravida magna a eros convallis, quis condimentum erat ornare. Duis cursus lorem nulla, ullamcorper lobortis purus malesuada id. In eu bibendum neque. Morbi eget consequat quam.

                        Proin sit amet massa posuere, condimentum dui vel, scelerisque arcu. Maecenas eu dui volutpat diam maximus ullamcorper vel eu mauris. Donec consectetur pretium metus, sed lobortis lacus posuere a. Duis ipsum tortor, laoreet ut leo et, accumsan vehicula mi. Nullam ut sodales orci. Maecenas euismod, tellus sed facilisis euismod, magna diam viverra tortor, id vestibulum tortor dui in urna. Praesent tristique commodo ipsum. Vestibulum a lorem ut diam sodales lacinia. Proin ac sollicitudin nisl, at tristique sapien. Quisque fermentum enim a odio tincidunt lacinia non consectetur magna. Integer sapien sapien, consectetur in consequat vel, ultrices eget nulla.

                        Ut a elit ut ex suscipit pellentesque non nec purus. Aenean ac porta magna, in laoreet neque. Phasellus aliquam felis eros, quis ullamcorper neque imperdiet a. Phasellus placerat imperdiet nibh et placerat. Aliquam erat volutpat. Donec fringilla, libero tristique dictum faucibus, sem elit pellentesque felis, vitae varius dui lectus ac dui. Cras imperdiet pretium suscipit. Cras mattis varius purus. Morbi porta, orci eu lobortis convallis, nulla ligula ultrices urna, eu tincidunt enim ligula sit amet neque. Sed imperdiet maximus est sit amet efficitur. Etiam non nunc ut nulla condimentum vestibulum et vel felis. Praesent sodales eleifend orci, egestas maximus tortor feugiat in. Ut efficitur vehicula vestibulum. Duis auctor leo at tempus sagittis. Integer eget nisl vel massa pharetra dictum. Nam pharetra vulputate pharetra.

                        Vestibulum sem magna, pharetra sed accumsan elementum, volutpat in sem. Praesent ipsum sapien, pretium in ullamcorper vitae, vulputate eu ex. Cras bibendum nulla at est pharetra, et condimentum tellus vestibulum. Sed in porttitor magna. Aliquam maximus euismod urna ut dapibus. Aliquam quis commodo ex. Quisque in tincidunt velit.

                        Suspendisse eu orci mi. Aliquam eget mauris gravida erat dapibus hendrerit nec ut lacus. In dictum mi ut lectus vulputate, vel sollicitudin erat fermentum. Aenean dignissim cursus nisi, ut venenatis mauris malesuada eget. Nunc nibh nisi, hendrerit a vestibulum sed, maximus eget felis. Sed iaculis nibh dignissim, feugiat quam ornare, dapibus lacus. Morbi maximus hendrerit magna eu ornare. Sed aliquam, magna vitae tempus imperdiet, quam est hendrerit massa, ut congue nibh enim eu elit. Ut ultricies eleifend sem, sit amet vulputate tellus porta gravida. Aliquam pellentesque lectus tempus, cursus sapien ut, tempor ligula. Quisque ultrices semper massa ut rutrum. Ut a justo pellentesque, lobortis felis quis, dignissim justo. Aenean quis diam vel arcu pretium aliquet. Phasellus eget faucibus erat.
                    """.trimIndent()
                )
            }
        )
    }
}