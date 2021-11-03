package com.github.psm.todo.android.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.psm.todo.android.ui.theme.TodoApplicationTheme

@Composable
fun DrawerMenuItem(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    title: @Composable () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        title()
    }
}

@Preview(showBackground = true)
@Composable
fun DrawerMenuItemPrev() {
    TodoApplicationTheme {
        DrawerMenuItem(
            modifier = Modifier.clickable {

            },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = null
                )
            },
            title = {
                Text(text = "Setting")
            }
        )
    }
}