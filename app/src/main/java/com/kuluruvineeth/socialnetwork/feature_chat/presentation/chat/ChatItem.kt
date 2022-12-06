package com.kuluruvineeth.socialnetwork.feature_chat.presentation.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.ProfilePictureSizeSmall
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.spaceMedium
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.spaceSmall
import com.kuluruvineeth.socialnetwork.feature_chat.domain.model.Chat


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChatItem(
    item: Chat,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier,
    onItemClick: (Chat) -> Unit,
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        onClick = {
            onItemClick(item)
        },
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = spaceSmall,
                    horizontal = spaceMedium
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = rememberImagePainter(
                    data = item.remoteUserProfilePictureUrl,
                    imageLoader = imageLoader
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(ProfilePictureSizeSmall)
                    .clip(CircleShape)
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = spaceSmall)
                    .weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = item.remoteUsername,
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(spaceSmall))
                    Text(text = item.timestamp.toString())
                }
                Spacer(modifier = Modifier.height(spaceSmall))
                Text(
                    text = item.lastMessage,
                    style = MaterialTheme.typography.body2,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    modifier = Modifier.heightIn(
                        min = MaterialTheme.typography.body2.fontSize.value.dp * 2.5f
                    )
                )
            }
        }
    }
}