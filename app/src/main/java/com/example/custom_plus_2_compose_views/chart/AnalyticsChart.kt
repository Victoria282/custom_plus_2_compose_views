package com.example.custom_plus_2_compose_views.chart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.max

data class AnalyticsChartData(
    val percents: List<Float>,
    val color: Color
)

@Composable
fun DrawAnalyticsChart(
    x: AnalyticsChartData,
    y: AnalyticsChartData,
    z: AnalyticsChartData,
    fontFamily: FontFamily,
    fourthForegroundColor: Color,
    secondaryForegroundColor: Color,
) {
    val breakdownIntoLevels = breakdownIntoLevels()
    val maxValue = breakdownIntoLevels.ceilValue.toFloat()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent
    ) {
        Column {
            DrawSection(
                modifier = Modifier.wrapContentHeight(Alignment.Bottom),
                levelNumber = breakdownIntoLevels.levels,
                breakdownIntoLevels = breakdownIntoLevels,
                secondaryForegroundColor = secondaryForegroundColor,
                fourthForegroundColor = fourthForegroundColor,
                fontFamily = fontFamily
            )
            Box(modifier = Modifier.weight(1f)) {
                DrawScale(
                    fourthForegroundColor = fourthForegroundColor,
                    secondaryForegroundColor = secondaryForegroundColor,
                    breakdownIntoLevels = breakdownIntoLevels,
                    fontFamily = fontFamily
                )
                Row(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                ) {
                    (0 until 3).forEachIndexed { index, _ ->
                        Spacer(modifier = Modifier.width(16.dp))
                        Box(modifier = Modifier.weight(1f)) {
                            Surface(
                                modifier = Modifier
                                    .fillMaxSize(),
                                color = Color.Transparent
                            ) {
                                DrawPillar(
                                    primaryForegroundColor = x.color,
                                    percent = x.percents[index] / maxValue,
                                    levels = breakdownIntoLevels.levels
                                )
                            }
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            Surface(
                                modifier = Modifier
                                    .fillMaxSize(),
                                color = Color.Transparent
                            ) {
                                DrawPillar(
                                    primaryForegroundColor = y.color,
                                    percent = y.percents[index] / maxValue,
                                    levels = breakdownIntoLevels.levels
                                )
                            }
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            Surface(
                                modifier = Modifier
                                    .fillMaxSize(),
                                color = Color.Transparent
                            ) {
                                DrawPillar(
                                    primaryForegroundColor = z.color,
                                    percent = z.percents[index] / maxValue,
                                    levels = breakdownIntoLevels.levels
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        if (index == 2) return@forEachIndexed
                        DrawVerticalDivider(fourthForegroundColor)
                    }
                }
            }
        }
    }
}

private data class BreakdownIntoLevels(
    val levels: Int,
    val oneChunkValue: Long,
    val ceilValue: Long
)

private fun breakdownIntoLevels(): BreakdownIntoLevels = BreakdownIntoLevels(
    oneChunkValue = 25,
    ceilValue = 100,
    levels = 4
)

@Composable
private fun DrawPillar(
    primaryForegroundColor: Color,
    percent: Float = 1f,
    levels: Int,
    paddingStart: Float = 0F
) {
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val canvasHeight = size.height
        val oneQuarterOfCanvas = canvasHeight / levels
        val offsetY = (canvasHeight) * (1 - percent)
        (1..levels).forEach { level ->
            if (oneQuarterOfCanvas * level > offsetY) {
                val topBorderOfSection = oneQuarterOfCanvas * (level - 1)
                val topY = max(topBorderOfSection, offsetY)
                val sizeY = oneQuarterOfCanvas - max(offsetY - topBorderOfSection, 0f)

                drawRect(
                    color = primaryForegroundColor,
                    topLeft = Offset(paddingStart, topY),
                    size = Size(56F, sizeY)
                )
            }
        }
    }
}

@Composable
private fun DrawScale(
    fourthForegroundColor: Color,
    breakdownIntoLevels: BreakdownIntoLevels,
    secondaryForegroundColor: Color,
    fontFamily: FontFamily
) {
    Column(modifier = Modifier.fillMaxSize()) {
        repeat(breakdownIntoLevels.levels) { index ->
            val levelNumber = breakdownIntoLevels.levels - index - 1
            DrawSection(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight(Alignment.Bottom),
                levelNumber = levelNumber,
                breakdownIntoLevels = breakdownIntoLevels,
                secondaryForegroundColor = secondaryForegroundColor,
                fourthForegroundColor = fourthForegroundColor,
                fontFamily = fontFamily
            )
        }
    }
}

@Composable
private fun DrawSection(
    modifier: Modifier,
    levelNumber: Int,
    breakdownIntoLevels: BreakdownIntoLevels,
    secondaryForegroundColor: Color,
    fourthForegroundColor: Color,
    fontFamily: FontFamily
) {
    Text(
        modifier = modifier.padding(bottom = 2.dp),
        text = (levelNumber * breakdownIntoLevels.oneChunkValue).toString(),
        fontSize = 12.sp,
        fontFamily = fontFamily,
        color = secondaryForegroundColor
    )
    DrawHorizontalDivider(fourthForegroundColor)
}

@Composable
fun DrawHorizontalDivider(fourthForegroundColor: Color) = Divider(
    color = fourthForegroundColor,
    modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)
)

@Composable
fun DrawVerticalDivider(fourthForegroundColor: Color) = Divider(
    color = fourthForegroundColor,
    modifier = Modifier
        .fillMaxHeight()
        .width(1.dp)
)