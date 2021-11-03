package com.github.psm.todo.android.ui.widget

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.psm.todo.android.ui.theme.IconColor
import com.github.psm.todo.android.ui.theme.TodoApplicationTheme

@Composable
fun UndoButton(
    onClick: () -> Unit
) {
    Text(
        modifier = Modifier
            .width(64.dp)
            .clip(CircleShape)
            .clickable {
                onClick()
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
        color = IconColor,
        textAlign = TextAlign.Center,
        maxLines = 1
    )
}

@Preview(showBackground = true)
@Composable
private fun UndoButtonPrev() {
    TodoApplicationTheme {
        UndoButton(
            onClick = { }
        )
    }
}