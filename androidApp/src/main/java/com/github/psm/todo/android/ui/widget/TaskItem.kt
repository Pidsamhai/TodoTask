package com.github.psm.todo.android.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.psm.shared.db.Task
import com.github.psm.todo.android.R
import com.github.psm.todo.android.ui.theme.ShadowColor
import com.github.psm.todo.android.ui.theme.TodoApplicationTheme
import java.util.*

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    task: Task,
    color: Color,
    click: (id: String) -> Unit = { }
) {
    Row(
        modifier = modifier
            .clickable {
                click(task.id)
            },
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(
                id = if (task.isCompleted) R.drawable.ic_check_circle
                else R.drawable.ic_unchecked_circle
            ),
            contentDescription = "Complete state icon",
            tint = if(task.isCompleted) ShadowColor else color
        )

        Text(
            text = task.title,
            style = MaterialTheme
                .typography
                .body1
                .copy(
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough
                    else TextDecoration.None
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskItemPrev() {
    val first = Task(
        id = UUID.randomUUID().toString(),
        isCompleted = true,
        title = "First task",
        timeStamp = System.currentTimeMillis()
    )

    val second = Task(
        id = UUID.randomUUID().toString(),
        isCompleted = false,
        title = "Second task",
        timeStamp = System.currentTimeMillis()
    )

    TodoApplicationTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TaskItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                task = first,
                color = Color.Red
            )
            TaskItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                task = second,
                color = Color.Green
            )
        }
    }
}