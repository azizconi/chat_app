package com.example.chatapp.common.navigation.nav_graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.core.NavigationGraph
import com.example.core.Screen

fun NavGraphBuilder.chatGraph(
    navController: NavHostController
) {


    navigation<NavigationGraph.ChatGraph>(Screen.ChatsScreen) {
        composable<Screen.ChatsScreen> {

        }
    }


}