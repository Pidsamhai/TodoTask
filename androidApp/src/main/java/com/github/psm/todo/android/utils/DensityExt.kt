package com.github.psm.todo.android.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp


@Composable fun Dp.toCPx(): Float {
    return with(LocalDensity.current) { this@toCPx.toPx() }
}