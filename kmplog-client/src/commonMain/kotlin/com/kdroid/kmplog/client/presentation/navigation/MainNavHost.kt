package com.kdroid.kmplog.client.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kdroid.kmplog.client.presentation.screens.home.Home
import org.koin.compose.koinInject

@Composable
fun MainNavHost() {
    val navController = rememberNavController()
    val navigator = koinInject<Navigator>()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        ObserveAsEvents(flow = navigator.navigationActions) { action ->
            when (action) {
                is NavigationAction.Navigate -> navController.navigate(
                    action.destination
                ) {
                    action.navOptions(this)
                }

                NavigationAction.NavigateUp -> navController.navigateUp()
            }
        }

        NavHost(
            navController = navController,
            startDestination = navigator.startDestination,
            modifier = Modifier.padding(innerPadding)) {

            composable<Destination.Home> { Home() }

            composable<Destination.Settings> {

            }

        }
    }
}