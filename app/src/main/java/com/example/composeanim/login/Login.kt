package com.example.composeanim.login

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2023/1/31 16:55
 * Desc  :
 */

@Composable
fun LoginPage() {
    val vm = viewModel<LoginVM>()
    var unm by remember {
        mutableStateOf("")
    }
    var pwd by remember {
        mutableStateOf("")
    }
    var big by remember {
        mutableStateOf(true)
    }
    var showLoading by remember {
        mutableStateOf(false)
    }
    val state by vm.flow.collectAsState(false)
    Log.i("Lanier", "v >> $state")
    if (state) {
        big = true
    }
    LaunchedEffect(key1 = showLoading) {
        delay(1000)
        showLoading = false
    }
    val width by animateDpAsState(if (big) 256.dp else 48.dp)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        OutlinedTextField(
            value = unm,
            onValueChange = {
                unm = it
            },
            label = {
                Text(text = "username")
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = pwd,
            onValueChange = {
                pwd = it
            },
            label = {
                Text(text = "password")
            },
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .width(width)
                .height(48.dp)
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colors.primary)
                .clickable {
                    if (big) {
                        big = false
                        vm.login()
                    } else {
                        showLoading = true
                    }
                }
        ) {
            androidx.compose.animation.AnimatedVisibility(
                visible = !big,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(24.dp)
            ) {
                CircularProgressIndicator(
                    color = Color.White
                )
            }
            if (big) {
                Text(
                    text = "login",
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        AnimatedVisibility(visible = showLoading) {
            Text(text = "Loading", color = MaterialTheme.colors.error)
        }
    }
}
