package com.kuluruvineeth.socialnetwork.feature_post.presentation.post_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.kuluruvineeth.socialnetwork.R
import com.kuluruvineeth.socialnetwork.core.domain.models.Comment
import com.kuluruvineeth.socialnetwork.core.domain.models.Post
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.*
import com.kuluruvineeth.socialnetwork.core.presentation.util.UiEvent
import com.kuluruvineeth.socialnetwork.core.presentation.util.asString
import com.kuluruvineeth.socialnetwork.presentation.components.ActionRow
import com.kuluruvineeth.socialnetwork.presentation.components.StandardTextField
import com.kuluruvineeth.socialnetwork.presentation.components.StandardToolbar
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PostDetailScreen(
    scaffoldState: ScaffoldState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: PostDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val commentTextFieldState = viewModel.commentTextFieldState.value

    val context = LocalContext.current
    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolbar(
            onNavigateUp = onNavigateUp,
            title = {
            Text(
                text = stringResource(id = R.string.your_feed),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground
            )
        },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = true,
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colors.surface)
        ){
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.background)
                ) {
                    Spacer(
                        modifier = Modifier
                            .height(spaceLarge)
                    )
                    Box(modifier = Modifier.fillMaxSize()){
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .offset(y = ProfilePictureSizeMedium / 2f)
                                .clip(MaterialTheme.shapes.medium)
                                .shadow(5.dp)
                                .background(MediumGray)
                        ) {
                            state.post?.let { post ->
                                Image(
                                    painter = rememberImagePainter(
                                        data = state.post.imageUrl,
                                        builder = {
                                            crossfade(true)
                                        }
                                    ),
                                    contentDescription = "Post image",
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(spaceLarge)
                                ) {
                                    ActionRow(
                                        username = "Kuluru Vineeth",
                                        modifier = Modifier.fillMaxWidth(),
                                        onLikeClick = { isLiked ->

                                        },
                                        onCommentClick = {

                                        },
                                        onShareClick = {

                                        },
                                        onUsernameClick = { username ->

                                        }
                                    )
                                    Spacer(modifier = Modifier.height(spaceSmall))
                                    Text(
                                        text = state.post.description,
                                        style = MaterialTheme.typography.body2,
                                    )
                                    Spacer(modifier = Modifier.height(spaceMedium))
                                    Text(
                                        text = stringResource(
                                            id = R.string.liked_by_x_people,
                                            post.likeCount
                                        ),
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.body2
                                    )
                                }
                            }
                        }
                            Image(
                                painter = rememberImagePainter(
                                    data = state.post?.profilePictureUrl,
                                    builder = {
                                        crossfade(true)
                                    }
                                ),
                                contentDescription = "Profile picture",
                                modifier = Modifier
                                    .size(ProfilePictureSizeMedium)
                                    .clip(CircleShape)
                                    .align(Alignment.TopCenter)
                            )
                            if (state.isLoadingPost) {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                Spacer(modifier = Modifier.height(spaceLarge))
            }
            items(state.comments) { comment ->
                Comment(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = spaceLarge,
                            vertical = spaceSmall
                        ),
                    comment = comment
                )
            }
        }
        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.surface)
                .fillMaxWidth()
                .padding(spaceLarge),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StandardTextField(
                text = commentTextFieldState.text,
                onValueChange = {
                    viewModel.onEvent(PostDetailEvent.EnteredComment(it))
                },
                backgroundColor = MaterialTheme.colors.background,
                modifier = Modifier
                    .weight(1f),
                hint = stringResource(id = R.string.enter_a_comment)
            )
            if(viewModel.commentState.value.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .size(24.dp),
                    strokeWidth = 2.dp
                )
            } else {
                IconButton(
                    onClick = {
                        viewModel.onEvent(PostDetailEvent.Comment)
                    },
                    enabled = commentTextFieldState.error == null
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        tint = if (commentTextFieldState.error == null) {
                            MaterialTheme.colors.primary
                        } else MaterialTheme.colors.background,
                        contentDescription = stringResource(id = R.string.send_comment)
                    )
                }
            }
        }

    }
}
