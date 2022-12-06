package com.kuluruvineeth.socialnetwork.presentation.util

import android.content.Intent
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.navDeepLink
import coil.ImageLoader
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
import com.kuluruvineeth.socialnetwork.feature_chat.presentation.message.MessageScreen
import com.kuluruvineeth.socialnetwork.feature_profile.presentation.search.SearchScreen
import com.kuluruvineeth.socialnetwork.presentation.splash.SplashScreen

@Composable
fun Navigation(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    imageLoader: ImageLoader
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
                scaffoldState = scaffoldState,
                onLogin = {
                    navController.popBackStack(
                        route = Screen.LoginScreen.route,
                        inclusive = true
                    )
                    navController.navigate(route = Screen.MainFeedScreen.route)
                }
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
                scaffoldState = scaffoldState,
                imageLoader = imageLoader
            )
        }
        composable(Screen.ChatScreen.route){
            ChatScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                imageLoader = imageLoader
            )
        }
        composable(
            route = Screen.MessageScreen.route + "/{chatId}/{remoteUserId}",
            arguments = listOf(
                navArgument(
                    name = "chatId"
                ){
                    type = NavType.StringType
                },
                navArgument("remoteUserId"){
                    type = NavType.StringType
                }
            )
        ){
            MessageScreen(
                chatId = "",
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                imageLoader = imageLoader
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
                onLogout = {
                           navController.navigate(route = Screen.LoginScreen.route)
                },
                onNavigate = navController::navigate,
                scaffoldState = scaffoldState,
                imageLoader = imageLoader
            )
        }
        composable(Screen.ActivityScreen.route){
            ActivityScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
            )
        }
        composable(Screen.CreatePostScreen.route){
            CreatePostScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                scaffoldState = scaffoldState,
                imageLoader = imageLoader
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
                scaffoldState = scaffoldState,
                imageLoader = imageLoader
            )
        }
        composable(
            route = Screen.PersonListScreen.route + "/{parentId}",
            arguments = listOf(
                navArgument("parentId"){
                    type = NavType.StringType
                }
            )
        ){
            PersonListScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                scaffoldState = scaffoldState,
                imageLoader = imageLoader
            )
        }
        composable(Screen.SearchScreen.route){
            SearchScreen(
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                imageLoader = imageLoader
            )
        }
        composable(
            route = Screen.PostDetailScreen.route + "/{postId}?shouldShowKeyboard={shouldShowKeyboard}",
            arguments = listOf(
                navArgument(
                    name = "postId"
                ){
                    type = NavType.StringType
                },
                navArgument(
                    name = "shouldShowKeyboard"
                ){
                    type = NavType.BoolType
                    defaultValue = false
                }
            ),
            deepLinks = listOf(
                navDeepLink {
                    action = Intent.ACTION_VIEW
                    uriPattern = "https://pl-coding.com/{postId}"
                }
            )
        ){
            val shouldShowKeyboard = it.arguments?.getBoolean("shouldShowKeyboard") ?: false
            println("POST ID: ${it.arguments?.getString("postId")}")
            PostDetailScreen(
                scaffoldState = scaffoldState,
                onNavigateUp = navController::navigateUp,
                onNavigate = navController::navigate,
                shouldShowKeyboard = shouldShowKeyboard,
                imageLoader = imageLoader
            )
        }
    }
}