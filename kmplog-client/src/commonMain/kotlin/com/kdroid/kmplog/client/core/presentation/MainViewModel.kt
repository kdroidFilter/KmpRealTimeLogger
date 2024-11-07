package com.kdroid.kmplog.client.core.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _isConnected = MutableStateFlow(false)
    val isConnected get() = _isConnected.asStateFlow()


}