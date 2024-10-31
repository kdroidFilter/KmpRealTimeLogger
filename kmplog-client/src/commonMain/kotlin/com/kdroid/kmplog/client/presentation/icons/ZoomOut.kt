package com.kdroid.kmplog.client.presentation.icons

import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val ZoomOut: ImageVector
    get() {
        if (_ZoomOut != null) {
            return _ZoomOut!!
        }
        _ZoomOut = ImageVector.Builder(
            name = "Remove",
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
                moveTo(15f, 8f)
                horizontalLineTo(1f)
                verticalLineTo(7f)
                horizontalLineToRelative(14f)
                verticalLineToRelative(1f)
                close()
            }
        }.build()
        return _ZoomOut!!
    }

private var _ZoomOut: ImageVector? = null
