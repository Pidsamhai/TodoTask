package com.github.psm.todo.android.ui.widget

import android.graphics.BlurMaskFilter
import android.graphics.Paint
import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.psm.todo.android.ui.theme.IconColor
import com.github.psm.todo.android.ui.theme.TodoApplicationTheme

/**
 * @param progress 0..1
 */
@Composable
fun ProfileIcon(
    modifier: Modifier = Modifier,
    progress: Float,
) {
    val twoDp = with(LocalDensity.current) { 4.dp.toPx() }

    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
                .clip(CircleShape)
                .background(Color.Black)
        )

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawArc(
                brush = SolidColor(
                    IconColor
                        .copy(alpha = 0.6f)
                ),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = true,
                style = Stroke(
                    width = twoDp * 0.8f,
                    join = StrokeJoin.Round,
                )
            )


            val paint = Paint().apply {
                isAntiAlias = true
                style = Paint.Style.STROKE
                color = Color(0xFFEE00FF).toArgb()
                strokeWidth = twoDp
                strokeCap = Paint.Cap.ROUND
                maskFilter = BlurMaskFilter(8f, BlurMaskFilter.Blur.SOLID)
            }

            drawIntoCanvas {
                it.nativeCanvas.drawArc(
                    RectF(
                        0f,
                        0f,
                        this.size.width,
                        this.size.height
                    ),
                    270f,
                    progress * 360f,
                    false,
                    paint,
                )
            }
        }

    }
}

@Preview
@Composable
fun ProfileIconPrev() {
    TodoApplicationTheme {
        ProfileIcon(
            modifier = Modifier
                .size(200.dp)
                .padding(24.dp)
                .background(Color.White),
            progress = 1f
        )
    }
}