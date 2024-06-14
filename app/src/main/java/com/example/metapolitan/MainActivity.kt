package com.example.metapolitan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.metapolitan.presentation.navigations.Screens
import com.example.metapolitan.presentation.screens.home.HomeScreen
import com.example.metapolitan.presentation.screens.movie_details.MovieDetailsScreen
import com.example.metapolitan.presentation.screens.search.SearchScreen
import com.example.metapolitan.presentation.theme.MetapolitanTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity(), NavController.OnDestinationChangedListener {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            //----------  init NavController -----------
            val navController = rememberNavController()
            navController.addOnDestinationChangedListener(this)

            MetapolitanTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MetapolitanApp(viewModel, navController)
                }
            }
        }
    }

    override fun onDestinationChanged(
        controller: NavController, destination: NavDestination, arguments: Bundle?
    ) {
//        // Controls whether the bottom navigation should be displayed on the current page or not
//        viewModel.isShowingBottomNavigation =
//            bottomNavItems.map { it.route }.contains(destination.route)
    }

}

@Composable
fun MetapolitanApp(viewModel: MainViewModel, navController: NavHostController) {

    Scaffold(containerColor = White)
    { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.HomeScreen.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(Screens.HomeScreen.route) { HomeScreen(navController) }
            composable(
                Screens.SearchScreen.route,
                enterTransition = {
                    expandHorizontally(
                        animationSpec = tween(700)
                    )
                },
                exitTransition = {
                    scaleOut (
                        animationSpec = tween(700)
                    )
                },
                popEnterTransition = {
                    expandHorizontally(
                        animationSpec = tween(700)
                    )
                },
                popExitTransition = {
                    scaleOut(
                        animationSpec = tween(700)
                    )
                }
            ) {
                SearchScreen(navController)
            }
            composable(
                Screens.MovieDetailsScreen.route,
                enterTransition = {
                    expandHorizontally(
                        animationSpec = tween(700)
                    )
                },
                exitTransition = {
                    scaleOut (
                        animationSpec = tween(700)
                    )
                },
                popEnterTransition = {
                    expandHorizontally(
                        animationSpec = tween(700)
                    )
                },
                popExitTransition = {
                    scaleOut(
                        animationSpec = tween(700)
                    )
                }) {
                MovieDetailsScreen(navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MetapolitanTheme {
        MetapolitanApp(viewModel = MainViewModel(), navController = rememberNavController())
    }
}