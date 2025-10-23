package com.example.quintaruta.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quintaruta.data.repository.UserRepository
import com.example.quintaruta.ui.screens.*
import com.example.quintaruta.viewmodel.TriviaViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val userRepo = remember(context) { UserRepository(context) }
    val start = if (userRepo.isLoggedIn()) "main" else "login"

    NavHost(navController = navController, startDestination = start) {

        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("main") { popUpTo("login") { inclusive = true } } },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }

        composable("register") {
            RegisterScreen(onRegisterSuccess = { navController.popBackStack() })
        }

        composable("main") {
            MainScreen(
                onOpenExplore = { navController.navigate("explore") },
                onCreateRoute = { navController.navigate("create_route") },
                onOpenProfile = { navController.navigate("profile") }
            )
        }

        composable("explore") {
            ExploreScreen(onRutaClick = { ruta ->
                navController.navigate("routeDetail/${ruta.id}")
            })
        }

        composable("create_route") {
            CreateOrEditRouteScreen(
                routeId = null,
                onSaved = { newId -> navController.navigate("routeDetail/$newId") },
                onCancel = { navController.popBackStack() }
            )
        }

        composable(
            "edit_route/{routeId}",
            arguments = listOf(navArgument("routeId") { type = NavType.LongType })
        ) { back ->
            val routeId = back.arguments?.getLong("routeId") ?: 0L
            CreateOrEditRouteScreen(
                routeId = routeId,
                onSaved = { navController.popBackStack() },
                onCancel = { navController.popBackStack() }
            )
        }

        composable("routeDetail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLong() ?: 0
            RouteDetailScreen(
                routeId = id,
                onBack = { navController.popBackStack() },
                onPoiClick = { poi ->
                    navController.navigate("poi_detail/$id/${poi.id}")
                }
            )
        }

        composable(
            route = "poi_detail/{routeId}/{poiId}",
            arguments = listOf(
                navArgument("routeId") { type = NavType.LongType },
                navArgument("poiId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val routeId = backStackEntry.arguments?.getLong("routeId") ?: 0L
            val poiId = backStackEntry.arguments?.getLong("poiId") ?: 0L
            PoiDetailScreen(
                routeId = routeId,
                poiId = poiId,
                onBack = { navController.popBackStack() },
                onOpenMap = { navController.navigate("map/$poiId") },
                onOpenTrivia = { navController.navigate("trivia/$poiId") }
            )
        }

        composable(
            route = "trivia/{poiId}",
            arguments = listOf(navArgument("poiId") { type = NavType.LongType })
        ) { back ->
            val poiId = back.arguments?.getLong("poiId") ?: 0L
            val triviaViewModel: TriviaViewModel = viewModel()

            TriviaScreen(
                poiId = poiId,
                viewModel = triviaViewModel,
                onFinish = { navController.popBackStack() }
            )
        }

        composable(
            route = "map/{poiId}",
            arguments = listOf(navArgument("poiId") { type = NavType.LongType })
        ) { back ->
            val poiId = back.arguments?.getLong("poiId") ?: 0
            MapScreen(
                poiId = poiId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
