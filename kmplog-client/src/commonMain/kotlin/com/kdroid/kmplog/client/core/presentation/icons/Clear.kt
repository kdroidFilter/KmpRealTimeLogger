package com.kdroid.kmplog.client.core.presentation.icons

import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Clear: ImageVector
    get() {
        if (_Clear != null) {
            return _Clear!!
        }
        _Clear = ImageVector.Builder(
            name = "DebugRestart",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(12.75f, 8f)
                arcToRelative(4.5f, 4.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, -8.61f, 1.834f)
                lineToRelative(-1.391f, 0.565f)
                arcTo(6.001f, 6.001f, 0f, isMoreThanHalf = false, isPositiveArc = false, 14.25f, 8f)
                arcTo(6f, 6f, 0f, isMoreThanHalf = false, isPositiveArc = false, 3.5f, 4.334f)
                verticalLineTo(2.5f)
                horizontalLineTo(2f)
                verticalLineToRelative(4f)
                lineToRelative(0.75f, 0.75f)
                horizontalLineToRelative(3.5f)
                verticalLineToRelative(-1.5f)
                horizontalLineTo(4.352f)
                arcTo(4.5f, 4.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 12.75f, 8f)
                close()
            }
        }.build()
        return _Clear!!
    }

private var _Clear: ImageVector? = null
