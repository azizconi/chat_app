package com.example.chatapp.common.navigation.nav_graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.chatapp.common.utils.sharedGraphViewModel
import com.example.chatapp.presentation.edit_profile.EditProfileScreen
import com.example.chatapp.presentation.profile.ProfileScreen
import com.example.chatapp.presentation.profile.ProfileViewModel
import com.example.core.NavigationGraph
import com.example.core.Screen

fun NavGraphBuilder.profileGraph(navController: NavHostController) {

    navigation<NavigationGraph.ProfileGraph>(startDestination = Screen.ProfileScreen) {
        composable<Screen.ProfileScreen> {
            val viewModel = it.sharedGraphViewModel<ProfileViewModel>(navController = navController)
            ProfileScreen(navController = navController, viewModel = viewModel)
        }
        composable<Screen.EditProfileScreen> {
            val viewModel = it.sharedGraphViewModel<ProfileViewModel>(navController = navController)
            EditProfileScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }

}