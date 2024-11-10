package com.example.chatapp.common

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.AccountCircle
import androidx.compose.material.icons.sharp.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.chatapp.R
import com.example.chatapp.common.models.BottomNavItem
import com.example.chatapp.common.navigation.Navigation
import com.example.chatapp.common.theme.ChatAppTheme
import com.example.chatapp.common.theme.Pink40
import com.example.chatapp.common.theme.Purple80
import com.example.chatapp.common.theme.PurpleGrey40
import com.example.chatapp.common.theme.PurpleGrey80
import com.example.core.NavigationGraph
import com.example.core.Screen
import kotlin.reflect.KClass

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme(darkTheme = false) {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        Log.e("TAG", "onCreate: ${navBackStackEntry?.destination?.parent?.route}")
                        AppBottomBar(
                            backStackEntry = navBackStackEntry,
                            onClick = {
                                navController.navigate(it.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            showBottomBar = shouldShowBottomBar(navBackStackEntry),
                        )
                    }
                ) {
                    it.calculateBottomPadding()
                    Navigation(navController)
                }
            }
        }
    }
}

@Composable
private fun AppBottomBar(
    backStackEntry: NavBackStackEntry?,
    onClick: (BottomNavItem) -> Unit,
    showBottomBar: Boolean = true,
) {

    val routes = listOf(
        BottomNavItem(
            route = NavigationGraph.ChatGraph,
            icon = Icons.Sharp.Send,
            contentDescription = stringResource(id = R.string.chat)
        ),
        BottomNavItem(
            route = NavigationGraph.ProfileGraph,
            icon = Icons.Sharp.AccountCircle,
            contentDescription = stringResource(id = R.string.profile)
        )
    )

    if (showBottomBar) {
        NavigationBar(
            containerColor = Purple80,
            tonalElevation = NavigationBarDefaults.Elevation
        ) {
            routes.forEach {
                NavigationBarItem(
                    selected = backStackEntry?.isCurrentGraph(it.route) == true,
                    icon = {
                        Icon(it.icon, contentDescription = null)
                    },
                    onClick = {
                        onClick(it)
                    },
                    label = {
                        Text(text = it.contentDescription)
                    },
//                    colors = NavigationBarItemDefaults.colors(
//                        selectedIconColor = Color.Black,
//                        selectedTextColor = Color.Black,
//                        indicatorColor = Color.Blue,
//                        unselectedIconColor = Color.White,
//                        unselectedTextColor = Color.White
//                    )
                )
            }
        }
    }

}

private fun NavBackStackEntry.isCurrentGraph(route: Any): Boolean {
    return destination.parent?.hierarchy?.any {
        it.hasRoute(route::class)
    } == true
}

private fun shouldShowBottomBar(backStackEntry: NavBackStackEntry?): Boolean {
    val isShowBottomNav = backStackEntry?.destination?.parent?.hierarchy?.any {
        it.hasRoute(NavigationGraph.AuthGraph::class)
    } == false && !backStackEntry.destination.hierarchy.any {
        it.hasRoute(Screen.EditProfileScreen::class) || it.hasRoute(Screen.ChatScreen::class)
    }

    return isShowBottomNav
}