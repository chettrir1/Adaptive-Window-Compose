package com.chettri.feed_compose.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chettri.feed_compose.data.DataProvider
import com.chettri.feed_compose.data.Sweets
import com.chettri.feed_compose.ui.screen.detail.SweetDetailScreen
import com.chettri.feed_compose.ui.screen.sweet.SweetsScreen

@Composable
fun FeedComposeApp(windowSizeClass: WindowSizeClass) {
    val navController = rememberNavController()
    val router = Router(navController)
    NavHost(navController = navController, startDestination = "/") {
        composable(Destination.Feed.path) {
            SweetsScreen(windowSizeClass = windowSizeClass) {
                router.showSweets(sweets = it)
            }
        }

        composable(
            Destination.Details.path,
            arguments = listOf(navArgument("sweetsId") { type = NavType.IntType })
        ) {
            val selectedSweetsId = it.arguments?.getInt("sweetsId") ?: 0
            SweetDetailScreen(
                sweets = DataProvider.getSweetsById(selectedSweetsId),
                windowSizeClass = windowSizeClass
            ) {
                navController.popBackStack()
            }
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