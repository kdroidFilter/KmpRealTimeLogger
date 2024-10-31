package com.kdroid.kmplog.client.presentation.icons


import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val ZoomIn: ImageVector
    get() {
        if (_ZoomIn != null) {
            return _ZoomIn!!
        }
        _ZoomIn = ImageVector.Builder(
            name = "Add",
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
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(14f, 7f)
                verticalLineToRelative(1f)
                horizontalLineTo(8f)
                verticalLineToRelative(6f)
                horizontalLineTo(7f)
                verticalLineTo(8f)
                horizontalLineTo(1f)
                verticalLineTo(7f)
                horizontalLineToRelative(6f)
                verticalLineTo(1f)
                horizontalLineToRelative(1f)
                verticalLineToRelative(6f)
                horizontalLineToRelative(6f)
                close()
            }
        }.build()
        return _ZoomIn!!
    }

private var _ZoomIn: ImageVector? = null
