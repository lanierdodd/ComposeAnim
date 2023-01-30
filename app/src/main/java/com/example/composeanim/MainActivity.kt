package com.example.composeanim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.composeanim.ui.theme.ComposeAnimTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAnimTheme {
                Simple2()
            }
        }
    }
}

@Preview
@Composable
fun Simple2() {
    var big by remember {
        mutableStateOf(false)
    }
    val width by animateDpAsState(if (big) 48.dp else 256.dp)
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .width(width)
                .height(48.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.Green)
                .clickable {
                    big = !big
                }
        ) {
            AnimatedVisibility(
                visible = big,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(24.dp)
            ) {
                CircularProgressIndicator(
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun Simple1() {
    var big by remember {
        mutableStateOf(false)
    }
    val size by animateDpAsState(if (big) 48.dp else 96.dp)
    // A surface container using the 'background' color from the theme
    Box(
        modifier = Modifier
            .background(Color.Yellow)
            .size(size)
            .clickable {
                big = !big
            }
    ) {
    }
}

@Preview
@Composable
fun Simple1Variant() {
    var big by remember {
        mutableStateOf(false)
    }
    val size = remember(big) {
        if (big) 96.dp else 48.dp
    }
    val anim = remember {
        Animatable(size, Dp.VectorConverter)
    }
    LaunchedEffect(key1 = big) {
        anim.snapTo(if (big) 192.dp else 0.dp)
        anim.animateTo(size)
    }
    Box(
        modifier = Modifier
            .background(Color.Yellow)
            .size(size)
            .clickable {
                big = !big
            }
    )
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeAnimTheme {
        Greeting("Android")
    }
}