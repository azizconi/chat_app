package com.example.chatapp.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.chatapp.common.navigation.nav_graphs.authGraph
import com.example.chatapp.common.navigation.nav_graphs.chatGraph
import com.example.chatapp.common.navigation.nav_graphs.profileGraph
import com.example.chatapp.common.viewModels.MainViewModel
import com.example.chatapp.presentation.auth.AuthScreen
import com.example.core.NavigationGraph
import com.example.core.Screen
import org.koin.androidx.compose.koinViewModel

@Composable
fun Navigation(
    navController: NavHostController,
    mainViewModel: MainViewModel = koinViewModel(),
) {

    val accessToken by mainViewModel.accessToken.collectAsState(NavigationGraph.EmptyGraph.toString())

    NavHost(
        navController = navController,
        startDestination = when (accessToken) {
            null -> NavigationGraph.AuthGraph
            NavigationGraph.EmptyGraph.toString() -> NavigationGraph.EmptyGraph
            else -> NavigationGraph.ChatGraph
        }
    ) {


        chatGraph(navController = navController)
        profileGraph(navController = navController)
        authGraph(navController = navController)

        navigation<NavigationGraph.EmptyGraph>(startDestination = Screen.EmptyScreen) {
            composable<Screen.EmptyScreen> {  }
        }

    }

}