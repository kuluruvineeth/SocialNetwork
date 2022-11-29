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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.kuluruvineeth.socialnetwork.R
import com.kuluruvineeth.socialnetwork.core.domain.models.Comment
import com.kuluruvineeth.socialnetwork.core.domain.models.Post
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.*
import com.kuluruvineeth.socialnetwork.presentation.components.ActionRow
import com.kuluruvineeth.socialnetwork.presentation.components.StandardToolbar

@Composable
fun PostDetailScreen(
    navController : NavController,
    post: Post
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolbar(navController = navController, title = {
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
                .fillMaxSize()
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
                            Image(
                                painter = painterResource(id = R.drawable.bullocks),
                                contentDescription = "Post Image",
                                contentScale = ContentScale.Crop,
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
                                    onLikeClick = {isLiked ->

                                    },
                                    onCommentClick = {

                                    },
                                    onShareClick = {

                                    },
                                    onUsernameClick = {username ->

                                    }
                                )
                                Spacer(modifier = Modifier.height(spaceSmall))
                                Text(
                                    text = post.description,
                                    style = MaterialTheme.typography.body2,
                                )
                                Spacer(modifier = Modifier.height(spaceMedium))
                                Text(
                                    text = stringResource(
                                        id = R.string.liked_by_x_people,
                                        post.likeCount
                                    ),
                                    style = MaterialTheme.typography.body2,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                        }
                        Image(
                            painter = painterResource(id = R.drawable.vineeth),
                            contentDescription = "Profile picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(ProfilePictureSizeMedium)
                                .clip(CircleShape)
                                .align(Alignment.TopCenter)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(spaceSmall))
            }
            items(20){
                Comment(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = spaceLarge,
                            vertical = 6.dp
                        ),
                    comment = Comment(
                        username = "Kuluru Vineeth",
                        comment = "If some idea truly excites you, no matter what go for it",

                        )
                )
            }
        }
    }

}

@Composable
fun Comment(
    modifier: Modifier = Modifier,
    comment: Comment = Comment(),
    onLikeClick : (Boolean) -> Unit = {}
) {
    Card(
        modifier = modifier
            .padding(spaceSmall),
        elevation = 5.dp,
        shape = MaterialTheme.shapes.medium,
        backgroundColor = HintGray
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(spaceMedium)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.vineeth),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(ProfilePictureSizeSmall)
                    )
                    Spacer(modifier = Modifier.width(spaceSmall))
                    Text(
                        text = comment.username,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onBackground
                    )
                }
                Text(
                    text = "2 days ago",
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.body2
                )
            }
            Spacer(modifier = Modifier.height(spaceMedium))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = comment.comment,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.weight(9f)
                )
                Spacer(modifier = Modifier.width(spaceMedium))
                IconButton(onClick = {
                    onLikeClick(comment.isLiked)
                },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = if(comment.isLiked){
                            stringResource(id = R.string.unlike)
                        }else{
                            stringResource(id = R.string.like)
                        },
                    )
                }
            }
            Spacer(modifier = Modifier.height(spaceMedium))
            Text(
                text = stringResource(id = R.string.liked_by_x_people,comment.likeCount),
                style = MaterialTheme.typography.body2,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}
