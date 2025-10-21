package com.example.quintaruta.ui.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quintaruta.data.repository.UserRepository
import com.example.quintaruta.ui.screens.ExploreScreen
import com.example.quintaruta.ui.screens.LoginScreen
import com.example.quintaruta.ui.screens.MainScreen
import com.example.quintaruta.ui.screens.MapScreen
import com.example.quintaruta.ui.screens.PoiDetailScreen
import com.example.quintaruta.ui.screens.ProfileScreen
import com.example.quintaruta.ui.screens.RegisterScreen
import com.example.quintaruta.ui.screens.RouteDetailScreen
import com.example.quintaruta.ui.screens.TriviaScreen
import com.example.quintaruta.ui.screens.CreateOrEditRouteScreen

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
                // onLogout = { userRepo.logout(); navController.navigate("login") { popUpTo("main"){ inclusive = true } } }
            )
        }

        composable("explore") {
            ExploreScreen(
                onRouteClick = { routeId -> navController.navigate("route_detail/$routeId") },
                onCreateRoute = { navController.navigate("create_route") }
            )
        }

        // VERSION DEMO
        composable("profile") {
            ProfileScreen(
                userName = "Patana",
                userEmail = "patana@trufillo.cl",
                profileImageUrl = null,
                userRoutes = emptyList(), // o lista vacía
                onEditRoute = { routeId ->
                    navController.navigate("edit_route/$routeId")
                }
            )
        }

        // TODO: CAMBIAR PROFILE A VERSION FINAL
        /*composable("profile") {
            val profileViewModel: ProfileViewModel = viewModel()
            val user = profileViewModel.user
            val routes = profileViewModel.userRoutes

            ProfileScreen(
                userName = user.name,
                userEmail = user.email,
                profileImageUrl = user.photoUrl,
                userRoutes = routes,
                onEditRoute = { routeId ->
                    navController.navigate("edit_route/$routeId")
                }
            )
        }*/


        composable("create_route") {
            CreateOrEditRouteScreen(
                routeId = null,
                onSaved = { newId -> navController.navigate("route_detail/$newId") },
                onCancel = { navController.popBackStack() }
            )
        }

        composable(
            route = "edit_route/{routeId}",
            arguments = listOf(navArgument("routeId") { type = NavType.LongType })) {
                back -> val routeId = back.arguments?.getLong("routeId") ?: 0L
                CreateOrEditRouteScreen(
                    routeId = routeId,
                    onSaved = { navController.popBackStack() },
                    onCancel = { navController.popBackStack() }
                )
            }

        composable(
            route = "route_detail/{routeId}",
            arguments = listOf(navArgument("routeId") { type = NavType.LongType }) ) {
                back -> val routeId = back.arguments?.getLong("routeId") ?: 0L
                RouteDetailScreen(
                    routeId = routeId,
                    onPoiClick = { poiId -> navController.navigate("poi_detail/$routeId/$poiId") },
                    onBack = { navController.popBackStack() }
                )
            }

        composable(
            route = "poi_detail/{routeId}/{poiId}",
            arguments = listOf(
                navArgument("routeId") { type = NavType.LongType },
                navArgument("poiId") { type = NavType.LongType })) {
                back ->
                    val routeId = back.arguments?.getLong("routeId") ?: 0L
                    val poiId = back.arguments?.getLong("poiId") ?: 0L
                PoiDetailScreen(
                    routeId = routeId,
                    poiId = poiId,
                    onOpenMap = { navController.navigate("map/$poiId") },
                    onStartTrivia = { navController.navigate("trivia/$poiId") },
                    onBack = { navController.popBackStack() }
                )
            }

        composable(
            route = "trivia/{poiId}",
            arguments = listOf(navArgument("poiId") { type = NavType.LongType }) ) {
                back -> val poiId = back.arguments?.getLong("poiId") ?: 0L
                    TriviaScreen(
                        poiId = poiId,
                        onFinish = { navController.popBackStack() }
                    )
            }

        composable(
            route = "map/{poiId}",
            arguments = listOf(navArgument("poiId") { type = NavType.LongType })) {
                back -> val poiId = back.arguments?.getLong("poiId") ?: 0L
                    MapScreen(
                        poiId = poiId,
                        onBack = { navController.popBackStack() }
                    )
                }

    }
}
