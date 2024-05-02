package ru.taxcom.commonrecycler.diagram.diagram

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

data class PieceCake(
    var startAngle: Float? = null,
    var sweepAngle: Float? = null,
    val percent: Float,
    val color: Color
)

private const val MAX_PERCENTS_VALUE = 100F
private const val COUNT_CHART_GROUPS = 3f

@Composable
fun DrawCircles(aGroup: PieceCake, bGroup: PieceCake, cGroup: PieceCake) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .height(100.dp)
            .width(100.dp), onDraw = {

            val isNullableData =
                aGroup.percent == 0F && bGroup.percent == 0F && cGroup.percent == 0F

            val canvasWidth = size.width
            val canvasHeight = size.height

            val aStartAngle = 0F
            val aAngle =
                if (isNullableData) (MAX_PERCENTS_VALUE / COUNT_CHART_GROUPS) * 3.6f else aGroup.percent * 3.6f

            drawArc(
                aGroup.color,
                aStartAngle,
                aAngle,
                useCenter = true,
                size = Size(canvasWidth, canvasWidth)
            )

            val bStartAngle: Float = aAngle
            val bAngle =
                if (isNullableData) (MAX_PERCENTS_VALUE / COUNT_CHART_GROUPS) * 3.6f else bGroup.percent * 3.6f

            drawArc(
                bGroup.color,
                bStartAngle,
                bAngle,
                useCenter = true,
                size = Size(canvasWidth, canvasWidth)
            )

            val cStartAngle: Float = aAngle + bAngle
            val cAngle =
                if (isNullableData) (MAX_PERCENTS_VALUE / COUNT_CHART_GROUPS) * 3.6f else cGroup.percent * 3.6f

            drawArc(
                cGroup.color,
                cStartAngle,
                cAngle,
                useCenter = true,
                size = Size(canvasWidth, canvasWidth)
            )

            val minHorW = listOf(canvasWidth, canvasHeight).min() / 2

            drawCircle(
                color = Color.Transparent,
                center = Offset(x = minHorW, y = minHorW),
                radius = 5f
            )

            val d = Math.toRadians((aStartAngle.toDouble() / 2))

            val aDividerY = minHorW * sin(d) + minHorW.toDouble()
            val aDividerX = minHorW * cos(d) + minHorW.toDouble()

            drawLine(
                start = Offset(x = minHorW, y = minHorW),
                end = Offset(x = aDividerX.toFloat(), y = aDividerY.toFloat()),
                color = Color.White,
                strokeWidth = 4f
            )

            val dd = Math.toRadians(cStartAngle.toDouble())

            val bDividerY = minHorW * sin(dd) + minHorW.toDouble()
            val bDividerX = minHorW * cos(dd) + minHorW.toDouble()

            drawLine(
                start = Offset(x = minHorW, y = minHorW),
                end = Offset(x = bDividerX.toFloat(), y = bDividerY.toFloat()),
                color = Color.White,
                strokeWidth = 4f
            )

            val ddd = Math.toRadians(bStartAngle.toDouble())

            val cDividerY = minHorW * sin(ddd) + minHorW.toDouble()
            val cDividerX = minHorW * cos(ddd) + minHorW.toDouble()

            drawLine(
                start = Offset(x = minHorW, y = minHorW),
                end = Offset(x = cDividerX.toFloat(), y = cDividerY.toFloat()),
                color = Color.White,
                strokeWidth = 4f
            )
        })
}