package com.kuluruvineeth.socialnetwork.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.kuluruvineeth.socialnetwork.R
import com.kuluruvineeth.socialnetwork.domain.models.Activity
import com.kuluruvineeth.socialnetwork.domain.models.User
import com.kuluruvineeth.socialnetwork.domain.util.ActivityAction
import com.kuluruvineeth.socialnetwork.domain.util.DateFormatUtil
import com.kuluruvineeth.socialnetwork.presentation.activity.ActivityItem
import com.kuluruvineeth.socialnetwork.presentation.components.Post
import com.kuluruvineeth.socialnetwork.presentation.components.StandardScaffold
import com.kuluruvineeth.socialnetwork.presentation.components.StandardToolbar
import com.kuluruvineeth.socialnetwork.presentation.profile.components.BannerSection
import com.kuluruvineeth.socialnetwork.presentation.profile.components.ProfileHeaderSection
import com.kuluruvineeth.socialnetwork.presentation.ui.theme.ProfilePictureSizeLarge
import com.kuluruvineeth.socialnetwork.presentation.ui.theme.spaceMedium
import com.kuluruvineeth.socialnetwork.presentation.ui.theme.spaceSmall
import com.kuluruvineeth.socialnetwork.presentation.util.Screen
import kotlin.random.Random

@Composable
fun ProfileScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        StandardToolbar(navController = navController, title = {
            Text(
                text = stringResource(id = R.string.your_profile),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground
            )
        },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = false,
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
        ){
            item{
                BannerSection(
                    modifier = Modifier
                        .aspectRatio(2.5f)
                )
            }
            item {
                ProfileHeaderSection(
                    user = User(
                        profilePictureUrl = "",
                        username = "Kuluru Vineeth",
                        description = "AgriRize Dream, Passionate Problem Solver",
                        followerCount = 234,
                        followingCount = 534,
                        postCount = 65
                    )
                )
            }
            items(20){
                Spacer(
                    modifier = Modifier
                        .height(spaceMedium)
                        .offset(y = -ProfilePictureSizeLarge / 2f),
                )
                Post(
                    post = com.kuluruvineeth.socialnetwork.domain.models.Post(
                        username = "Kuluru Vineeth",
                        imageUrl = "",
                        profilePictureUrl = "",
                        description = "Agririze(Close to my heart) is the passion that i am " + "living with for sure will make it go live by the end of 2023",
                        likeCount = 17,
                        commentCount = 10
                    ),
                    showProfileImage = false,
                    onPostClick = {
                        navController.navigate(Screen.PostDetailScreen.route)
                    },
                    modifier = Modifier
                        .offset(y = -ProfilePictureSizeLarge / 2f)
                )
            }
        }
    }
}