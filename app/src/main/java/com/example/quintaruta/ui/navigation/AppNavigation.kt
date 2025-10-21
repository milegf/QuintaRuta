package com.example.quintaruta.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quintaruta.data.repository.UserRepository
import com.example.quintaruta.ui.screens.CreateOrEditRouteScreen
import com.example.quintaruta.ui.screens.EditProfileScreen
import com.example.quintaruta.ui.screens.ExploreScreen
import com.example.quintaruta.ui.screens.LoginScreen
import com.example.quintaruta.ui.screens.MainScreen
import com.example.quintaruta.ui.screens.MapScreen
import com.example.quintaruta.ui.screens.PoiDetailScreen
import com.example.quintaruta.ui.screens.ProfileScreen
import com.example.quintaruta.ui.screens.RegisterScreen
import com.example.quintaruta.ui.screens.RouteDetailScreen
import com.example.quintaruta.ui.screens.TriviaScreen
import com.example.quintaruta.viewmodel.CreateOrEditRouteViewModel
import com.example.quintaruta.viewmodel.ProfileViewModel

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
            RegisterScreen(
                onRegisterSuccess = { navController.popBackStack() }
            )
        }

        composable("main") {
            MainScreen(
                onOpenExplore = { navController.navigate("explore") },
                onOpenProfile = { navController.navigate("profile") },
                onCreateRoute = { navController.navigate("create_route") }
            )
        }

        composable("explore") {
            ExploreScreen(
                onRouteClick = { routeId -> navController.navigate("route_detail/$routeId") },
                onCreateRoute = { navController.navigate("create_route") }
            )
        }

        composable("profile") {
            val profileViewModel: ProfileViewModel = viewModel()
            val uiState by profileViewModel.uiState.collectAsState()

            // Forzamos la recarga de datos al entrar en la pantalla
            LaunchedEffect(Unit) {
                profileViewModel.loadProfileData()
            }

            ProfileScreen(
                uiState = uiState,
                onEditRoute = { routeId -> navController.navigate("edit_route/$routeId") },
                onDeleteRoute = { routeId -> profileViewModel.deleteRoute(routeId) },
                onEditProfileClick = { navController.navigate("edit_profile") }
            )
        }

        composable("edit_profile") {
            val editProfileViewModel: ProfileViewModel = viewModel()
            val uiState by editProfileViewModel.uiState.collectAsState()

            EditProfileScreen(
                currentName = uiState.userName,
                onSave = {
                    editProfileViewModel.updateUserName(it)
                    navController.popBackStack()
                },
                onCancel = { navController.popBackStack() }
            )
        }

        composable("create_route") {
            CreateOrEditRouteScreen(
                routeId = null, // null para indicar que es una creación
                onSaved = { newId -> navController.navigate("route_detail/$newId") { popUpTo("explore") } },
                onCancel = { navController.popBackStack() }
            )
        }

        composable(
            route = "edit_route/{routeId}",
            arguments = listOf(navArgument("routeId") { type = NavType.LongType })
        ) {
            val routeId = it.arguments?.getLong("routeId") ?: 0L
            CreateOrEditRouteScreen(
                routeId = routeId,
                onSaved = {
                    // Navegación especial para forzar la actualización del perfil
                    navController.popBackStack()
                    navController.navigate("profile") { popUpTo("profile") { inclusive = true } }
                },
                onCancel = { navController.popBackStack() }
            )
        }

        composable(
            route = "route_detail/{routeId}",
            arguments = listOf(navArgument("routeId") { type = NavType.LongType })
        ) { back ->
            val routeId = back.arguments?.getLong("routeId") ?: 0L
            RouteDetailScreen(
                routeId = routeId,
                onReturnToHome = { navController.navigate("main") { popUpTo("main") { inclusive = true } } }
            )
        }

        composable(
            route = "poi_detail/{routeId}/{poiId}",
            arguments = listOf(
                navArgument("routeId") { type = NavType.LongType },
                navArgument("poiId") { type = NavType.LongType }
            )
        ) { back ->
            val routeId = back.arguments?.getLong("routeId") ?: 0L
            val poiId = back.arguments?.getLong("poiId") ?: 0L
            PoiDetailScreen(
                routeId = routeId,
                poiId = poiId,
                onOpenMap = { navController.navigate("map/$poiId") },
                onStartTrivia = { navController.navigate("trivia/$poiId") },
                onBack = { navController.popBackStack() },
                onReturnToHome = { navController.navigate("main") { popUpTo("main") { inclusive = true } } }
            )
        }

        composable(
            route = "trivia/{poiId}",
            arguments = listOf(navArgument("poiId") { type = NavType.LongType })
        ) { back ->
            val poiId = back.arguments?.getLong("poiId") ?: 0L
            TriviaScreen(
                poiId = poiId,
                onFinish = { navController.popBackStack() }
            )
        }

        composable(
            route = "map/{poiId}",
            arguments = listOf(navArgument("poiId") { type = NavType.LongType })
        ) { back ->
            val poiId = back.arguments?.getLong("poiId") ?: 0L
            MapScreen(
                poiId = poiId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}