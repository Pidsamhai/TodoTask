package com.github.psm.todo.android.ui.widget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.psm.todo.android.ui.theme.IconColor
import com.github.psm.todo.android.ui.theme.ShadowColor
import com.github.psm.todo.android.ui.theme.TodoApplicationTheme

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    categoryTitle: String,
    taskCount: Int,
    progress: Float,
    progressColor: Color = MaterialTheme.colors.secondary
) {
    Card(
        modifier = modifier,
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "%d tasks".format(taskCount),
                style = MaterialTheme.typography.subtitle2
            )
            Text(
                text = categoryTitle,
                fontWeight = FontWeight.SemiBold
            )
            BoxWithConstraints {
                Divider(
                    modifier = Modifier.clip(CircleShape),
                    thickness = 4.dp,
                    color = ShadowColor
                )

                Divider(
                    modifier = Modifier
                        .width(width = maxWidth * progress)
                        .clip(CircleShape),
                    thickness = 4.dp,
                    color = progressColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryItemPrev() {
    TodoApplicationTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            CategoryItem(
                modifier = Modifier
                    .width(200.dp)
                    .aspectRatio(16f / 9),
                categoryTitle = "Business",
                taskCount = 40,
                progress = 0.5f
            )
        }
    }
}