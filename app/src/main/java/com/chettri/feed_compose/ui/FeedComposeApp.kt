package com.chettri.feed_compose.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chettri.feed_compose.data.Sweets

@Composable
fun FeedComposeApp(modifier: WindowSizeClass) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "/") {
        composable(Destination.Feed.path) {

        }
    }


}

private sealed interface Destination {
    val base: String
    val path: String

    data object Feed : Destination {
        override val base: String = "/"
        override val path: String = base
    }

    data object Details : Destination {
        override val base: String = "/show"
        override val path: String = "$base/{sweetsId}"
    }
}

private class Router(val navController: NavController) {
    fun showSweets(sweets: Sweets) {
        navController.navigate("${Destination.Details.base}/${sweets.id}")
    }

}