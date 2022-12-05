package com.kuluruvineeth.socialnetwork.feature_profile.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.paging.compose.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceIn
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.kuluruvineeth.socialnetwork.R
import com.kuluruvineeth.socialnetwork.core.domain.models.User
import com.kuluruvineeth.socialnetwork.presentation.components.Post
import com.kuluruvineeth.socialnetwork.feature_profile.presentation.profile.components.BannerSection
import com.kuluruvineeth.socialnetwork.feature_profile.presentation.profile.components.ProfileHeaderSection
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.ProfilePictureSize
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.spaceMedium
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.spaceSmall
import com.kuluruvineeth.socialnetwork.core.presentation.util.UiEvent
import com.kuluruvineeth.socialnetwork.core.presentation.util.asString
import com.kuluruvineeth.socialnetwork.core.util.Screen
import com.kuluruvineeth.socialnetwork.core.util.toPx
import com.kuluruvineeth.socialnetwork.feature_post.presentation.person_list.PostEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileScreen(
    imageLoader: ImageLoader,
    userId: String? = null,
    onNavigate: (String) -> Unit = {},
    onLogout: () -> Unit = {},
    scaffoldState: ScaffoldState,
    profilePictureSize: Dp = ProfilePictureSize,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val pagingState = viewModel.pagingState.value
    val lazyListState = rememberLazyListState()
    var toolbarState = viewModel.toolbarState.value
    //var expandedRatio = viewModel.expandedRatio.value
    var iconGroupWidth by remember {
        mutableStateOf(0)
    }
    var iconHorizontalCenterLength =
        (LocalConfiguration.current.screenWidthDp.dp.toPx() / 4f -
                (ProfilePictureSize /4f).toPx() -
                spaceSmall.toPx())/2f
    var totalToolbarOffsetY by remember {
        mutableStateOf(0f)
    }
    val isFirstItemVisible = lazyListState.firstVisibleItemIndex == 0
    println("SCROLLED DOWN $isFirstItemVisible")
    //println("TOOLBAR Offset $toolbarOffsetY")
    val iconSizeExpanded = 35.dp

    val toolbarHeightCollapsed = 75.dp
    val imageCollapsedOffsetY = remember {
        (toolbarHeightCollapsed - ProfilePictureSize /2f)/2f
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

    val state = viewModel.state.value
    val nestedScrollConnection = remember {
        object : NestedScrollConnection{
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                if(delta > 0f && lazyListState.firstVisibleItemIndex != 0){
                    return Offset.Zero
                }
                val newOffset = viewModel.toolbarState.value.toolbarOffsetY + delta
                viewModel.setToolbarOffsetY(newOffset.coerceIn(
                    minimumValue = -maxOffset.toPx(),
                    maximumValue = 0f
                ))
                //totalToolbarOffsetY += viewModel.toolbarState.value.toolbarOffsetY
                viewModel.setExpandedRatio((viewModel.toolbarState.value.toolbarOffsetY + maxOffset.toPx()) /maxOffset.toPx() )
                println("EXPANDED RATIO: ${toolbarState.expandedRatio}")
                return Offset.Zero
            }
        }
    }
    val context = LocalContext.current
    LaunchedEffect(key1 = true){
        viewModel.setExpandedRatio(1f)
        viewModel.getProfile(userId)
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
                is PostEvent.OnLiked -> {

                }
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
                state.profile?.let{ profile ->
                    ProfileHeaderSection(
                        user = User(
                            userId = profile.userId,
                            profilePictureUrl = profile.profilePictureUrl,
                            username = profile.username,
                            description = profile.bio,
                            followerCount = profile.followerCount,
                            followingCount = profile.followingCount,
                            postCount = profile.postCount
                        ),
                        isFollowing = profile.isFollowing,
                        isOwnProfile = profile.isOwnProfile,
                        onLogoutClick = {
                            viewModel.onEvent(ProfileEvent.ShowLogoutDialog)
                        },
                        onEditClick = {
                            onNavigate(Screen.EditProfileScreen.route + "/${profile.userId}")
                        }
                    )
                }
            }
            items(pagingState.items.size){i ->
                val post = pagingState.items[i]
                if(i >= pagingState.items.size - 1 && !pagingState.endReached && !pagingState.isLoading){
                    viewModel.loadNextPosts()
                }
                Post(
                    post = post,
                    imageLoader = imageLoader,
                    showProfileImage = false,
                    onPostClick = {
                        onNavigate(Screen.PostDetailScreen.route + "/${post.id}")
                    },
                    onCommentClick = {
                        onNavigate(Screen.PostDetailScreen.route + "/${post.id}?shouldShowKeyboard=true")
                    },
                    onLikeClick = {
                        viewModel.onEvent(ProfileEvent.LikePost(post.id))
                    }
                )
            }
            item { 
                Spacer(modifier = Modifier.height(90.dp))
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
        ) {
            state.profile?.let{ profile ->
                BannerSection(
                    modifier = Modifier
                        .height(
                            (bannerHeight * toolbarState.expandedRatio).coerceIn(
                                minimumValue = toolbarHeightCollapsed,
                                maximumValue = bannerHeight
                            )
                        ),
                    leftIconModifier = Modifier
                        .graphicsLayer {
                            translationY = (1f - toolbarState.expandedRatio) *
                                    -iconCollapsedOffsetY.toPx()
                            translationX = (1f - toolbarState.expandedRatio) *
                                    iconHorizontalCenterLength

                        },
                    rightIconModifier = Modifier
                        .graphicsLayer {
                            translationY = (1f - toolbarState.expandedRatio) *
                                    -iconCollapsedOffsetY.toPx()
                            translationX = (1f - toolbarState.expandedRatio) *
                                    -iconHorizontalCenterLength
                        },
                    imageLoader = imageLoader,
                    topSkills = profile.topSkills,
                    shouldShowGitHub = profile.gitHubUrl != null && profile.gitHubUrl.isNotBlank(),
                    shouldShowInstagram = profile.instagramUrl != null && profile.instagramUrl.isNotBlank(),
                    shouldShowLinkedIn = profile.linkedInUrl != null && profile.linkedInUrl.isNotBlank(),
                    bannerUrl = profile.bannerUrl
                )
                Image(
                    painter = rememberImagePainter(
                        data = profile.profilePictureUrl,
                        imageLoader = imageLoader
                    ),
                    contentDescription = stringResource(id = R.string.profile_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .graphicsLayer {
                            translationY = -ProfilePictureSize.toPx() / 2f -
                                    (1f - toolbarState.expandedRatio) * imageCollapsedOffsetY.toPx()
                            transformOrigin = TransformOrigin(
                                pivotFractionX = 0.5f,
                                pivotFractionY = 0f
                            )
                            val scale = 0.5f + toolbarState.expandedRatio * 0.5f
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
        if (state.isLogoutDialogVisible) {
            Dialog(onDismissRequest = {
                viewModel.onEvent(ProfileEvent.DismissLogoutDialog)
            }) {
                Column(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colors.surface,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(spaceMedium)
                ) {
                    Text(text = stringResource(id = R.string.do_you_want_to_logout))
                    Spacer(modifier = Modifier.height(spaceMedium))
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(
                            text = stringResource(id = R.string.no).uppercase(),
                            color = MaterialTheme.colors.onBackground,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                viewModel.onEvent(ProfileEvent.DismissLogoutDialog)
                            }
                        )
                        Spacer(modifier = Modifier.width(spaceMedium))
                        Text(
                            text = stringResource(id = R.string.yes).uppercase(),
                            color = MaterialTheme.colors.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                viewModel.onEvent(ProfileEvent.Logout)
                                viewModel.onEvent(ProfileEvent.DismissLogoutDialog)
                                onLogout()
                            }
                        )
                    }
                }
            }
        }
    }

}