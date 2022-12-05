package com.kuluruvineeth.socialnetwork.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import com.kuluruvineeth.socialnetwork.presentation.components.StandardScaffold
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.SocialNetworkTheme
import com.kuluruvineeth.socialnetwork.presentation.util.Navigation
import com.kuluruvineeth.socialnetwork.core.util.Screen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SocialNetworkTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val scaffoldState = rememberScaffoldState()
                    StandardScaffold(
                        navController = navController,
                        showBottomBar = shouldShowBottomBar(navBackStackEntry),
                        state = scaffoldState,
                        modifier = Modifier.fillMaxSize(),
                        onFabClick = {
                            navController.navigate(Screen.CreatePostScreen.route)
                        }
                    ) {
                        Navigation(navController,scaffoldState,imageLoader)
                    }
                }
            }
        }
    }

    private fun shouldShowBottomBar(backStackEntry: NavBackStackEntry?): Boolean{
        val doesRouteMatch = backStackEntry?.destination?.route in listOf(
                Screen.MainFeedScreen.route,
                Screen.ChatScreen.route,
                Screen.ActivityScreen.route
        )
        val isOwnProfile = backStackEntry?.destination?.route == "${Screen.ProfileScreen.route}?userId={userId}" &&
                backStackEntry?.arguments?.getString("userId") == null
        return doesRouteMatch || isOwnProfile
    }
}