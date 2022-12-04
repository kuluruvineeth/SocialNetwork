package com.kuluruvineeth.socialnetwork.presentation.util

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.kuluruvineeth.socialnetwork.core.domain.models.Post
import com.kuluruvineeth.socialnetwork.core.util.Screen
import com.kuluruvineeth.socialnetwork.presentation.MainFeedScreen
import com.kuluruvineeth.socialnetwork.presentation.PersonListScreen
import com.kuluruvineeth.socialnetwork.feature_activity.presentation.activity.ActivityScreen
import com.kuluruvineeth.socialnetwork.presentation.chat.ChatScreen
import com.kuluruvineeth.socialnetwork.feature_post.presentation.create_post.CreatePostScreen
import com.kuluruvineeth.socialnetwork.feature_profile.presentation.edit_profile.EditProfileScreen
import com.kuluruvineeth.socialnetwork.feature_auth.presentation.login.LoginScreen
import com.kuluruvineeth.socialnetwork.feature_post.presentation.post_detail.PostDetailScreen
import com.kuluruvineeth.socialnetwork.feature_profile.presentation.profile.ProfileScreen
import com.kuluruvineeth.socialnetwork.feature_auth.presentation.register.RegisterScreen
import com.kuluruvineeth.socialnetwork.feature_profile.presentation.search.SearchScreen
import com.kuluruvineeth.socialnetwork.presentation.splash.SplashScreen

@Composable
fun Navigation(
    navController: NavHostController,
    scaffoldState: ScaffoldState
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ){
        composable(Screen.SplashScreen.route){
            SplashScreen(
                onPopBackStack = navController::popBackStack,
                onNavigate = navController::navigate
            )
        }
        composable(Screen.LoginScreen.route){
            LoginScreen(
                onNavigate = navController::navigate,
                scaffoldState = scaffoldState
            )
        }
        composable(Screen.RegisterScreen.route){
            RegisterScreen(
                navController = navController,
                scaffoldState = scaffoldState
            )
        }
        composable(Screen.MainFeedScreen.route){
            MainFeedScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                scaffoldState = scaffoldState
            )
        }
        composable(Screen.ChatScreen.route){
            ChatScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate
            )
        }
        composable(
            Screen.ProfileScreen.route + "?userId={userId}",
            arguments = listOf(
                navArgument(name = "userId"){
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ){
            ProfileScreen(
                userId = it.arguments?.getString("userId"),
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                scaffoldState = scaffoldState
            )
        }
        composable(Screen.ActivityScreen.route){
            ActivityScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate
            )
        }
        composable(Screen.CreatePostScreen.route){
            CreatePostScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                scaffoldState = scaffoldState
            )
        }
        composable(
            Screen.EditProfileScreen.route + "/{userId}",
            arguments = listOf(
                navArgument(name = "userId"){
                    type = NavType.StringType
                }
            )
        ){
            EditProfileScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                scaffoldState = scaffoldState
            )
        }
        composable(Screen.PersonListScreen.route){
            PersonListScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate
            )
        }
        composable(Screen.SearchScreen.route){
            SearchScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate
            )
        }
        composable(
            route = Screen.PostDetailScreen.route + "/{postId}",
            arguments = listOf(
                navArgument(
                    name = "postId"
                ){
                    type = NavType.StringType
                }
            )
        ){
            PostDetailScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
            )
        }
    }
}