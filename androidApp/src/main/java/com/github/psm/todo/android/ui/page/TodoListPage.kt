package com.github.psm.todo.android.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.psm.shared.db.Task
import com.github.psm.todo.android.ui.theme.ContentHorizonPadding
import com.github.psm.todo.android.ui.theme.IconColor
import com.github.psm.todo.android.ui.theme.TextColor
import com.github.psm.todo.android.ui.theme.TodoApplicationTheme
import com.github.psm.todo.android.ui.widget.*
import com.github.psm.todo.android.viewModel.TaskListViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun TodoListPage(
    navigateToAddTask: () -> Unit,
    viewModel: TaskListViewModel,
    openDrawer: () -> Unit = { }
) {
    val tasks by viewModel.tasks.collectAsState(initial = listOf())
    TodoListPageContent(
        navigateToAddTask = navigateToAddTask,
        tasks = tasks,
        toggleCompleted = {
            viewModel.toggleCompleted(it)
        },
        openDrawer = openDrawer,
        onDelete = { viewModel.deleteTask(it) }
    )
}

@Composable
private fun TodoListPageContent(
    navigateToAddTask: () -> Unit,
    tasks: List<Task>,
    toggleCompleted: (id: String) -> Unit,
    openDrawer: () -> Unit = { },
    onDelete: (String) -> Unit = { }
) {

    val scope = rememberCoroutineScope()
    var job: Job? = null

    Scaffold(
        topBar = {
            TodoListTopAppbar(openDrawer = openDrawer)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAddTask
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "Add task icon",
                    tint = Color.White
                )
            }
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = ContentHorizonPadding
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                modifier = Modifier.padding(
                    top = 24.dp
                ),
                text = "What's up, Joy!",
                style = MaterialTheme.typography.h3,
                fontWeight = FontWeight.Bold,
                color = TextColor
            )

            Text(
                text = "Category".uppercase(),
                style = MaterialTheme.typography.subtitle2,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CategoryItem(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(16f / 9),
                    categoryTitle = "Business",
                    taskCount = 15,
                    progress = 0.8f
                )

                CategoryItem(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(16f / 9),
                    categoryTitle = "Personal",
                    taskCount = 50,
                    progress = 0.1f,
                    progressColor = Color.Red
                )
            }

            Text(
                text = "Today's tasks".uppercase(),
                style = MaterialTheme.typography.subtitle2,
            )

            LazyColumn(
                modifier = Modifier
            ) {
                items(tasks) { item ->
                    var state: SwipeState by remember {
                        mutableStateOf(SwipeState.Close)
                    }

                    when (state) {
                        SwipeState.Close -> job?.cancel()
                        SwipeState.Open -> {
                            job = scope.launch {
                                delay(1000)
                                onDelete(item.id)
                                state = SwipeState.Close
                            }
                        }
                    }

                    SwipeToDeleteItem(
                        swipeState = state,
                        onSwipe = {
                            state = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        content = {
                            TaskItem(
                                modifier = Modifier
                                    .background(Color.White)
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                click = { id ->
                                    toggleCompleted(id)
                                },
                                task = item,
                                color = Color.Red
                            )
                        },
                        actions = {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        state = SwipeState.Close
                                    }
                                    .border(
                                        width = 2.dp,
                                        brush = SolidColor(IconColor),
                                        shape = CircleShape
                                    )
                                    .padding(
                                        vertical = 6.dp,
                                        horizontal = 12.dp
                                    ),
                                text = "Undo",
                                color = IconColor
                            )
                        },
                        actionWidth = 72.dp,
                        hint = {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Delete,
                                    contentDescription = "delete",
                                    tint = IconColor
                                )

                                Text(
                                    text = "The task was deleted",
                                    color = IconColor
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TodoListPagePrev() {
    val tasks = listOf(
        Task(
            id = UUID.randomUUID().toString(),
            isCompleted = true,
            title = "First task",
            timeStamp = System.currentTimeMillis()
        ),
        Task(
            id = UUID.randomUUID().toString(),
            isCompleted = false,
            title = "Second task",
            timeStamp = System.currentTimeMillis()
        )
    )
    TodoApplicationTheme {
        TodoListPageContent(
            navigateToAddTask = { },
            tasks = tasks,
            toggleCompleted = { }
        )
    }
}