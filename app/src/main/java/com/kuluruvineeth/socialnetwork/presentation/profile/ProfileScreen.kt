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
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceIn
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.kuluruvineeth.socialnetwork.presentation.ui.theme.ProfilePictureSize
import com.kuluruvineeth.socialnetwork.presentation.ui.theme.spaceMedium
import com.kuluruvineeth.socialnetwork.presentation.ui.theme.spaceSmall
import com.kuluruvineeth.socialnetwork.presentation.util.Screen
import com.kuluruvineeth.socialnetwork.presentation.util.toDp
import com.kuluruvineeth.socialnetwork.presentation.util.toPx
import kotlin.random.Random

@Composable
fun ProfileScreen(
    navController: NavController,
    profilePictureSize: Dp = ProfilePictureSize,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val lazyListState = rememberLazyListState()
    var toolbarOffsetY = viewModel.toolbarOffsetY.value
    var expandedRatio = viewModel.expandedRatio.value
    var iconGroupWidth by remember {
        mutableStateOf(0)
    }
    var iconHorizontalCenterLength =
        (LocalConfiguration.current.screenWidthDp.dp.toPx() / 4f -
                (ProfilePictureSize/4f).toPx() -
                spaceSmall.toPx())/2f
    var totalToolbarOffsetY by remember {
        mutableStateOf(0f)
    }
    val isFirstItemVisible = lazyListState.firstVisibleItemIndex == 0
    println("SCROLLED DOWN $isFirstItemVisible")
    println("TOOLBAR Offset $toolbarOffsetY")
    val iconSizeExpanded = 35.dp

    val toolbarHeightCollapsed = 75.dp
    val imageCollapsedOffsetY = remember {
        (toolbarHeightCollapsed - ProfilePictureSize/2f)/2f
    }
    val iconCollapsedOffsetY = remember {
        (toolbarHeightCollapsed - iconSizeExpanded) / 2f
    }
    val bannerHeight =
        (LocalConfiguration.current.screenWidthDp / 2.5f).dp
    val toolbarHeightExpanded = remember{
        bannerHeight + ProfilePictureSize
    }
    val maxOffset = remember {
        toolbarHeightExpanded - toolbarHeightCollapsed
    }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection{
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                if(delta > 0f && lazyListState.firstVisibleItemIndex != 0){
                    return Offset.Zero
                }
                val newOffset = toolbarOffsetY + delta
                toolbarOffsetY = newOffset.coerceIn(
                    minimumValue = -maxOffset.toPx(),
                    maximumValue = 0f
                )
                totalToolbarOffsetY += toolbarOffsetY
                expandedRatio = ((toolbarOffsetY + maxOffset.toPx()) /maxOffset.toPx() )
                println("EXPANDED RATIO: $expandedRatio")
                return Offset.Zero
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
            state = lazyListState
        ){
            item{
                Spacer(modifier = Modifier.height(
                    toolbarHeightExpanded - ProfilePictureSize / 2f
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
                ){
                    navController.navigate(Screen.EditProfileScreen.route)
                }
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
                modifier = Modifier
                    .height(
                        (bannerHeight * expandedRatio).coerceIn(
                            minimumValue = toolbarHeightCollapsed,
                            maximumValue = bannerHeight
                        )
                    ),
                leftIconModifier = Modifier
                    .graphicsLayer {
                        translationY = (1f - expandedRatio) *
                                -iconCollapsedOffsetY.toPx()
                        translationX = (1f - expandedRatio) *
                                iconHorizontalCenterLength

                    },
                rightIconModifier = Modifier
                    .graphicsLayer {
                        translationY = (1f - expandedRatio) *
                                -iconCollapsedOffsetY.toPx()
                        translationX = (1f - expandedRatio) *
                                -iconHorizontalCenterLength
                    },
            )
            Image(
                painter = painterResource(id = R.drawable.vineeth),
                contentDescription = stringResource(id = R.string.profile_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .graphicsLayer {
                        translationY = -ProfilePictureSize.toPx() / 2f -
                                (1f - expandedRatio) * imageCollapsedOffsetY.toPx()
                        transformOrigin = TransformOrigin(
                            pivotFractionX = 0.5f,
                            pivotFractionY = 0f
                        )
                        val scale = 0.5f + expandedRatio * 0.5f
                        scaleX = scale
                        scaleY = scale
                    }
                    .size(ProfilePictureSize)
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