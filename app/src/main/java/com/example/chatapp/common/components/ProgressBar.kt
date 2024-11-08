package com.example.chatapp.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.core.Resource


@Composable
fun <T> DialogProgressBar(
    result: Resource<T>,
) {

    if (result is Resource.Loading) {
        DialogProgressBar()
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DialogProgressBar(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
) {

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier
            .size(90.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier

                .padding(16.dp),
        ) {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(strokeCap = StrokeCap.Round)
            }


        }
    }

}