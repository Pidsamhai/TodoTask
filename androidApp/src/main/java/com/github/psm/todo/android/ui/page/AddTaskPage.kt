package com.github.psm.todo.android.ui.page

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.psm.todo.android.R
import com.github.psm.todo.android.ui.theme.ShadowColor
import com.github.psm.todo.android.ui.theme.TextColor
import com.github.psm.todo.android.ui.theme.TodoApplicationTheme
import com.github.psm.todo.android.ui.widget.CustomTextField
import com.github.psm.todo.android.viewModel.TaskListViewModel

@Composable
fun AddTaskPage(
    viewModel: TaskListViewModel,
    navigateBack: () -> Unit
) {
    AddTaskPageContent(
        navigateBack = navigateBack,
        onSave = {
            viewModel.addNewTask(it)
            navigateBack()
        }
    )
}

@Composable
private fun AddTaskPageContent(
    navigateBack: () -> Unit,
    onSave: (String) -> Unit
) {
    val focusRequester = FocusRequester()

    var text by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_round_expand_less),
                        contentDescription = "New task icon",
                        tint = Color.White
                    )
                },
                text = {
                    Text(
                        text = "New task",
                        color = Color.White
                    )
                },
                onClick = {
                    if (text.isNotEmpty()) {
                        onSave(text)
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .clip(CircleShape)
                    .border(
                        color = ShadowColor,
                        width = 2.dp,
                        shape = CircleShape
                    )
                    .align(alignment = Alignment.TopEnd)
                    .padding(12.dp)
                    .clickable {
                        navigateBack()
                    }
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "Close icon",
                    tint = TextColor
                )
            }
            CustomTextField(
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .focusRequester(focusRequester),
                placeholder = "Enter new task",
                value = text,
                onValueChange = {
                    text = it
                },
                textStyle = MaterialTheme
                    .typography
                    .body1
                    .copy(
                        fontSize = 24.sp
                    )
            )
        }
    }
}

@Preview
@Composable
fun AddTaskPagePrev() {
    TodoApplicationTheme {
        AddTaskPageContent(
            navigateBack = { },
            onSave = { }
        )
    }
}