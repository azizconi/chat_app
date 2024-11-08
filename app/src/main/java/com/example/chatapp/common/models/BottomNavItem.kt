package com.example.chatapp.common.models

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: Any,
    val icon: ImageVector,
    val contentDescription: String
)
