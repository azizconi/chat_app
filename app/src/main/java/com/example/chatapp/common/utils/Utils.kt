package com.example.chatapp.common.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State

fun <T> MutableState<T>.asState(): State<T> = this