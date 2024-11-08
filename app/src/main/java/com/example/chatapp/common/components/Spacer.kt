package com.example.chatapp.common.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.example.chatapp.common.constants.StandardSpaceSize

@Composable
fun HorizontalSpacer(
    dp: Dp = StandardSpaceSize
) {
    Spacer(modifier = Modifier.width(dp))
}

@Composable
fun VerticalSpacer(
    dp: Dp = StandardSpaceSize
) {
    Spacer(modifier = Modifier.height(dp))
}

