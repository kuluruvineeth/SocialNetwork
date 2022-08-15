package com.kuluruvineeth.socialnetwork.presentation.util

sealed class Screen(val route: String){
    object SplashScreen : Screen("splash_Screen")
    object LoginScreen : Screen("login_Screen")
    object RegisterScreen : Screen("register_Screen")
    object MainFeedScreen : Screen("main_feed_Screen")
    object PostDetailScreen : Screen("post_detail_Screen")
    object ChatScreen : Screen("chat_Screen")
    object MessageScreen : Screen("message_Screen")
    object ProfileScreen : Screen("profile_Screen")
    object EditProfileScreen : Screen("edit_profile_Screen")
    object PersonListScreen : Screen("person_list_Screen")
    object CreatePostScreen : Screen("create_post_Screen")
    object ActivityScreen : Screen("activity_Screen")
    object SearchScreen : Screen("search_Screen")
}
