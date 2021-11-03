package com.github.psm.todo.android.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.psm.todo.android.R
import com.github.psm.todo.android.ui.theme.IconColor
import com.github.psm.todo.android.ui.theme.TextColor
import com.github.psm.todo.android.ui.theme.TodoApplicationTheme
import com.github.psm.todo.android.ui.widget.DrawerMenuItem
import com.github.psm.todo.android.ui.widget.ProfileIcon

@Composable
fun AppDrawerPager(
    modifier: Modifier = Modifier,
    closeDrawer: () -> Unit = { }
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopEnd
    ) {
        IconButton(
            modifier = Modifier
                .padding(16.dp)
                .border(
                    shape = CircleShape,
                    width = 2.dp,
                    brush = SolidColor(
                        IconColor.copy(
                            alpha = 0.8f
                        )
                    )
                ),
            onClick = closeDrawer
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_round_chevron_left),
                contentDescription = "back icon",
                tint = Color.White
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileIcon(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                progress = 0.5f
            )

            Spacer(modifier = Modifier.size(32.dp))

            Text(
                text = "Joy Mitchell",
                style = MaterialTheme.typography.h3,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.size(32.dp))

            DrawerMenuItem(
                modifier = Modifier.clickable {

                },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_round_bookmark_border),
                        contentDescription = null,
                        tint = IconColor
                    )
                },
                title = {
                    Text(
                        text = "Template",
                        color = Color.White
                    )
                }
            )

            DrawerMenuItem(
                modifier = Modifier.clickable {

                },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outline_auto_awesome_mosaic),
                        contentDescription = null,
                        tint = IconColor
                    )
                },
                title = {
                    Text(
                        text = "Categories",
                        color = Color.White
                    )
                }
            )

            DrawerMenuItem(
                modifier = Modifier.clickable {

                },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outline_pie_chart),
                        contentDescription = null,
                        tint = IconColor
                    )
                },
                title = {
                    Text(
                        text = "Analytics",
                        color = Color.White
                    )
                }
            )

            DrawerMenuItem(
                modifier = Modifier.clickable {

                },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outline_settings),
                        contentDescription = null,
                        tint = IconColor
                    )
                },
                title = {
                    Text(
                        text = "Settings",
                        color = Color.White
                    )
                }
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppDrawerPagerPrev() {
    TodoApplicationTheme {
        AppDrawerPager(
            modifier = Modifier
                .background(TextColor)
        )
    }
}