package com.kdroid.kmplog.client.core.presentation.icons

import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val Automatic: ImageVector
    get() {
        if (_Automatic != null) {
            return _Automatic!!
        }
        _Automatic = ImageVector.Builder(
            name = "Hdr_auto",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(276f, 680f)
                horizontalLineToRelative(76f)
                lineToRelative(40f, -112f)
                horizontalLineToRelative(176f)
                lineToRelative(40f, 112f)
                horizontalLineToRelative(76f)
                lineTo(520f, 240f)
                horizontalLineToRelative(-80f)
                close()
                moveToRelative(138f, -176f)
                lineToRelative(64f, -182f)
                horizontalLineToRelative(4f)
                lineToRelative(64f, 182f)
                close()
                moveToRelative(66f, 376f)
                quadToRelative(-83f, 0f, -156f, -31.5f)
                reflectiveQuadTo(197f, 763f)
                reflectiveQuadToRelative(-85.5f, -127f)
                reflectiveQuadTo(80f, 480f)
                reflectiveQuadToRelative(31.5f, -156f)
                reflectiveQuadTo(197f, 197f)
                reflectiveQuadToRelative(127f, -85.5f)
                reflectiveQuadTo(480f, 80f)
                reflectiveQuadToRelative(156f, 31.5f)
                reflectiveQuadTo(763f, 197f)
                reflectiveQuadToRelative(85.5f, 127f)
                reflectiveQuadTo(880f, 480f)
                reflectiveQuadToRelative(-31.5f, 156f)
                reflectiveQuadTo(763f, 763f)
                reflectiveQuadToRelative(-127f, 85.5f)
                reflectiveQuadTo(480f, 880f)
                moveToRelative(0f, -80f)
                quadToRelative(133f, 0f, 226.5f, -93.5f)
                reflectiveQuadTo(800f, 480f)
                reflectiveQuadToRelative(-93.5f, -226.5f)
                reflectiveQuadTo(480f, 160f)
                reflectiveQuadToRelative(-226.5f, 93.5f)
                reflectiveQuadTo(160f, 480f)
                reflectiveQuadToRelative(93.5f, 226.5f)
                reflectiveQuadTo(480f, 800f)
            }
        }.build()
        return _Automatic!!
    }

private var _Automatic: ImageVector? = null
