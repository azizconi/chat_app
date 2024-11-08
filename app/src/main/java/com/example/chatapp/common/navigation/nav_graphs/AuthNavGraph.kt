package com.example.chatapp.common.navigation.nav_graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.chatapp.presentation.auth.AuthScreen
import com.example.chatapp.presentation.otp.OtpScreen
import com.example.chatapp.presentation.register.RegisterScreen
import com.example.core.NavigationGraph
import com.example.core.Screen

fun NavGraphBuilder.authGraph(
    navController: NavHostController,
) {

    navigation<NavigationGraph.AuthGraph>(
        startDestination = Screen.AuthScreen,
    ) {

        composable<Screen.AuthScreen> {
            AuthScreen(navController = navController)
        }

        composable<Screen.OtpScreen> {
            val optScreen: Screen.OtpScreen = it.toRoute()
            OtpScreen(navController = navController, phone = optScreen.phone)
        }

        composable<Screen.RegisterScreen> {
            val model: Screen.RegisterScreen = it.toRoute()
            RegisterScreen(
                navController = navController,
                phone = model.phone,
            )
        }

    }

}