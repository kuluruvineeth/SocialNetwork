package com.kuluruvineeth.socialnetwork.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
import me.onebone.toolbar.*
import kotlin.random.Random

@Composable
fun ProfileScreen(
    navController: NavController
) {
    var toolbarOffsetY by remember {
        mutableStateOf(0f)
    }
    val toolbarHeightCollapsed = 56.dp
    val bannerHeight =
        (LocalConfiguration.current.screenWidthDp / 2.5f).dp
    val toolbarHeightExpanded = remember{
        bannerHeight + ProfilePictureSizeLarge
    }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection{
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetY + delta
                return super.onPreScroll(available, source)
            }
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .nestedScroll(nestedScrollConnection)
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
        ){
            item{
                Spacer(modifier = Modifier.height(
                    toolbarHeightExpanded - ProfilePictureSizeLarge / 2f
                ))
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
                        .height(spaceMedium),
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
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
        ) {
            BannerSection(
                modifier = Modifier.height(bannerHeight)
            )
            Image(
                painter = painterResource(id = R.drawable.vineeth),
                contentDescription = stringResource(id = R.string.profile_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .graphicsLayer {
                        translationY = -ProfilePictureSizeLarge.toPx() / 2f
                    }
                    .size(ProfilePictureSizeLarge)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.onSurface,
                        shape = CircleShape
                    )
            )
        }
    }

}