package com.example.composeanim.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2023/1/31 17:23
 * Desc  :
 */
class LoginVM: ViewModel() {

    val flow = MutableSharedFlow<Boolean>(replay = 1)

    fun login() {
        viewModelScope.launch {
            flow.tryEmit(false)
            delay(3000)
            flow.tryEmit(true)
        }
    }
}