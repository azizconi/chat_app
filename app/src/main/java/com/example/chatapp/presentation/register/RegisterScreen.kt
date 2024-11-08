package com.example.chatapp.presentation.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.core.Screen
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(
    navController: NavHostController,
    phone: String,
    viewModel: RegisterScreenViewModel = koinViewModel(),
) {




    Scaffold(

    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {

        }
    }


}