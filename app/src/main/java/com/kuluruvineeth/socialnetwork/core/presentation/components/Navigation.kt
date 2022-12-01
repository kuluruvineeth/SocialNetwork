package com.kuluruvineeth.socialnetwork.presentation.util

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
            SplashScreen(navController = navController)
        }
        composable(Screen.LoginScreen.route){
            LoginScreen(
                navController = navController,
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
        composable(Screen.CreatePostScreen.route){
            CreatePostScreen(navController = navController)
        }
        composable(Screen.EditProfileScreen.route){
            EditProfileScreen(navController = navController)
        }
        composable(Screen.PersonListScreen.route){
            PersonListScreen(navController = navController)
        }
        composable(Screen.SearchScreen.route){
            SearchScreen(navController = navController)
        }
        composable(Screen.PostDetailScreen.route){
            PostDetailScreen(
                navController = navController,
                post = Post(
                    username = "Kuluru Vineeth",
                    imageUrl = "",
                    profilePictureUrl = "",
                    description = "Agririze(Close to my heart) is the passion that i am " +
                            "living with for sure will make it go live by the end of 2023" +
                            "gririze(Close to my heart) is the passion that i am \" + \n" +
                            "                    \"living with for sure will make it go live by the end of 2023",
                    likeCount = 17,
                    commentCount = 10

                )
            )
        }
    }
}