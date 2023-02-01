package com.example.composeanim.progress

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlin.random.Random

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2023/1/31 17:48
 * Desc  :
 */
@Composable
fun ProgressBarView() {
    var progressBarWidth by remember {
        mutableStateOf(7f)
    }
    var backgroundProgressBarWidth by remember {
        mutableStateOf(3f)
    }
    var round by remember {
        mutableStateOf(false)
    }
    var progress by remember {
        mutableStateOf(0f)
    }
    var backgroundProgressBarColor by remember {
        mutableStateOf(Color.Gray)
    }
    var progressBarColor by remember {
        mutableStateOf(Color.Black)
    }
    var showColorDialog by remember {
        mutableStateOf(false)
    }
    var isProgressColor by remember {
        mutableStateOf(true)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "foreground")
        Slider(
            value = progressBarWidth,
            onValueChange = { progressBarWidth = it },
            valueRange = 0f..20f,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "background")
        Slider(
            value = backgroundProgressBarWidth,
            onValueChange = { backgroundProgressBarWidth = it },
            valueRange = 0f..20f,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            progress = Random.nextInt(100).toFloat()
        }) {
            Text(text = "random progress")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "round")
            Switch(checked = round, onCheckedChange = { round = it })
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            isProgressColor = true
            showColorDialog = true
        }) {
            Text(text = "progress color")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            isProgressColor = false
            showColorDialog = true
        }) {
            Text(text = "background color")
        }
        Spacer(modifier = Modifier.height(32.dp))
        CircularProgressBar(
            progress = progress,
            progressBarWidth = progressBarWidth.dp,
            progressBarColor = progressBarColor,
            backgroundProgressBarWidth = backgroundProgressBarWidth.dp,
            backgroundProgressBarColor = backgroundProgressBarColor,
            roundBorder = round,
            modifier = Modifier
                .size(180.dp)
        )
    }
    if (showColorDialog) {
        CustomDialogWithResultExample(
            onDismiss = { showColorDialog = false },
            onNegativeClick = { showColorDialog = false },
            onPositiveClick = {
                if (isProgressColor) {
                    progressBarColor = it
                } else {
                    backgroundProgressBarColor = it
                }
                showColorDialog = false
            }
        )
    }
}

@Composable
private fun CircularProgressBar(
    modifier: Modifier = Modifier,
    progress: Float = 0f,
    progressMax: Float = 100f,
    progressBarColor: Color = Color.Black,
    progressBarWidth: Dp = 7.dp,
    backgroundProgressBarColor: Color = Color.Gray,
    backgroundProgressBarWidth: Dp = 3.dp,
    roundBorder: Boolean = false,
    startAngle: Float = 0f
) {
    val target = (progress / progressMax) * 360f
    val anim = remember {
        Animatable(target)
    }
    LaunchedEffect(key1 = progress) {
        anim.animateTo(target, tween(easing = LinearOutSlowInEasing))
    }
    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasSize = size.minDimension
        val radius = canvasSize / 2 - maxOf(backgroundProgressBarWidth, progressBarWidth).toPx() / 2
        drawCircle(
            color = backgroundProgressBarColor,
            radius = radius,
            center = size.center,
            style = Stroke(width = backgroundProgressBarWidth.toPx())
        )

        drawArc(
            color = progressBarColor,
            startAngle = 270f + startAngle,
            sweepAngle = anim.value,
            useCenter = false,
            topLeft = size.center - Offset(radius, radius),
            size = Size(radius * 2, radius * 2),
            style = Stroke(
                width = progressBarWidth.toPx(),
                cap = if (roundBorder) StrokeCap.Round else StrokeCap.Butt
            )
        )
    }
}

/**
 * copy by stackflow
 */
@Composable
private fun CustomDialogWithResultExample(
    onDismiss: () -> Unit,
    onNegativeClick: () -> Unit,
    onPositiveClick: (Color) -> Unit
) {
    var red by remember { mutableStateOf(0f) }
    var green by remember { mutableStateOf(0f) }
    var blue by remember { mutableStateOf(0f) }

    val color = Color(
        red = red.toInt(),
        green = green.toInt(),
        blue = blue.toInt(),
        alpha = 255
    )

    Dialog(onDismissRequest = onDismiss) {

        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp)
        ) {

            Column(modifier = Modifier.padding(8.dp)) {

                Text(
                    text = "Select Color",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Color Selection
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {


                    Column {

                        Text(text = "Red ${red.toInt()}")
                        Slider(
                            value = red,
                            onValueChange = { red = it },
                            valueRange = 0f..255f,
                            onValueChangeFinished = {}
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = "Green ${green.toInt()}")
                        Slider(
                            value = green,
                            onValueChange = { green = it },
                            valueRange = 0f..255f,
                            onValueChangeFinished = {}
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = "Blue ${blue.toInt()}")
                        Slider(
                            value = blue,
                            onValueChange = { blue = it },
                            valueRange = 0f..255f,
                            onValueChangeFinished = {}
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            border = BorderStroke(1.dp, Color.DarkGray),
                            color = color,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                        ) {}
                    }
                }

                // Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onNegativeClick) {
                        Text(text = "CANCEL")
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    TextButton(onClick = {
                        onPositiveClick(color)
                    }) {
                        Text(text = "OK")
                    }
                }
            }
        }
    }
}
