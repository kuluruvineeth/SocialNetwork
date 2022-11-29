package com.kuluruvineeth.socialnetwork.presentation.splash

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.NavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kuluruvineeth.socialnetwork.core.presentation.MainActivity
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.SocialNetworkTheme
import com.kuluruvineeth.socialnetwork.core.util.Screen
import com.kuluruvineeth.socialnetwork.core.util.Constants
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashScreenTest{

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @RelaxedMockK
    lateinit var navController: NavController

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
    }

    @Test
    fun splashScreen_displaysAndDisappears() = testDispatcher.runBlockingTest{
        composeTestRule.setContent {
            SocialNetworkTheme {
                SplashScreen(
                    navController = navController,
                    dispatcher = testDispatcher
                )
            }
        }
        composeTestRule
            .onNodeWithContentDescription("Logo")
            .assertExists()

        advanceTimeBy(Constants.SPLASH_SCREEN_DURATION)
        verify {
            navController.popBackStack()
            navController.navigate(Screen.LoginScreen.route)
        }
    }
}