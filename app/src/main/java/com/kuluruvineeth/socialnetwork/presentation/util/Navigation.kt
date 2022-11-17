package com.kuluruvineeth.socialnetwork.presentation.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kuluruvineeth.socialnetwork.presentation.MainFeedScreen
import com.kuluruvineeth.socialnetwork.presentation.activity.ActivityScreen
import com.kuluruvineeth.socialnetwork.presentation.chat.ChatScreen
import com.kuluruvineeth.socialnetwork.presentation.login.LoginScreen
import com.kuluruvineeth.socialnetwork.presentation.profile.ProfileScreen
import com.kuluruvineeth.socialnetwork.presentation.register.RegisterScreen
import com.kuluruvineeth.socialnetwork.presentation.splash.SplashScreen

@Composable
fun Navigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ){
        composable(Screen.SplashScreen.route){
            SplashScreen(navController = navController)
        }
        composable(Screen.LoginScreen.route){
            LoginScreen(navController = navController)
        }
        composable(Screen.RegisterScreen.route){
            RegisterScreen(navController = navController)
        }
        composable(Screen.MainFeedScreen.route){
            MainFeedScreen(navController = navController)
        }
        composable(Screen.ChatScreen.route){
            ChatScreen(navController = navController)
        }
        composable(Screen.ProfileScreen.route){
            ProfileScreen(navController = navController)
        }
        composable(Screen.ActivityScreen.route){
            ActivityScreen(navController = navController)
        }
    }
}