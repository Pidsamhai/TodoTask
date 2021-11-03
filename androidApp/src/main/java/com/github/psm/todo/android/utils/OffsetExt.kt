package com.github.psm.todo.android.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

fun Offset.toIntOffset(): IntOffset {
    return IntOffset(
        x = this.x.roundToInt(),
        y = this.y.roundToInt()
    )
}