package com.github.psm.todo.android.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.psm.todo.android.ui.theme.IconColor
import com.github.psm.todo.android.ui.theme.ShadowColor
import com.github.psm.todo.android.ui.theme.TextColor
import com.github.psm.todo.android.ui.theme.TodoApplicationTheme

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle.Default
) {
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
    val textFieldValue = textFieldValueState.copy(text = value)

    Box(
        modifier = modifier.padding(16.dp)
    ) {
        if (value.isEmpty()) {
            Text(
                text = placeholder,
                style = textStyle,
                color = IconColor
            )
        }
        BasicTextField(
            value = textFieldValue,
            onValueChange = {
                textFieldValueState = it
                if (value != it.text) {
                    onValueChange(it.text)
                }
            },
            textStyle = textStyle,
            cursorBrush = SolidColor(MaterialTheme.colors.secondary),
        )
    }
}

@Preview
@Composable
fun TextFieldPrev() {
    var text by remember {
        mutableStateOf("")
    }
    TodoApplicationTheme {
        CustomTextField(
            placeholder = "Enter task name",
            value = text,
            onValueChange = {
                text = it
            }
        )
    }
}