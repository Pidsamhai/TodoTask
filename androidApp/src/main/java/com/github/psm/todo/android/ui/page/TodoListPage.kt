package com.github.psm.todo.android.ui.page

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.psm.shared.db.Task
import com.github.psm.todo.android.ui.theme.ContentHorizonPadding
import com.github.psm.todo.android.ui.theme.TextColor
import com.github.psm.todo.android.ui.theme.TodoApplicationTheme
import com.github.psm.todo.android.ui.widget.CategoryItem
import com.github.psm.todo.android.ui.widget.TaskItem
import com.github.psm.todo.android.ui.widget.TodoListTopAppbar
import com.github.psm.todo.android.viewModel.TaskListViewModel
import kotlinx.coroutines.flow.observeOn
import java.util.*

@Composable
fun TodoListPage(
    navigateToAddTask: () -> Unit,
    viewModel: TaskListViewModel
) {
    val tasks by viewModel.tasks.collectAsState(initial = listOf())
    TodoListPageContent(
        navigateToAddTask = navigateToAddTask,
        tasks = tasks,
        toggleCompleted = {
            viewModel.toggleCompleted(it)
        }
    )
}

@Composable
private fun TodoListPageContent(
    navigateToAddTask: () -> Unit,
    tasks: List<Task>,
    toggleCompleted: (id: String) -> Unit
) {
    val tasksScrollState = rememberScrollState()
    Scaffold(
        topBar = { TodoListTopAppbar() },
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

            Column(
                modifier = Modifier
                    .verticalScroll(tasksScrollState)
            ) {
                tasks.forEach {
                    TaskItem(
                        click = { id ->
                                toggleCompleted(id)
                        },
                        task = it,
                        color = Color.Green
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