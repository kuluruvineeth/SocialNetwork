package com.kuluruvineeth.socialnetwork.feature_chat.presentation.message

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import androidx.compose.foundation.lazy.items
import coil.compose.rememberImagePainter
import com.kuluruvineeth.socialnetwork.R
import com.kuluruvineeth.socialnetwork.core.presentation.components.SendTextField
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.DarkerGreen
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.ProfilePictureSizeSmall
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.spaceMedium
import com.kuluruvineeth.socialnetwork.feature_chat.domain.model.Message
import com.kuluruvineeth.socialnetwork.feature_chat.presentation.message.components.OwnMessage
import com.kuluruvineeth.socialnetwork.feature_chat.presentation.message.components.RemoteMessage
import com.kuluruvineeth.socialnetwork.presentation.components.StandardToolbar
import okio.ByteString.Companion.decodeBase64
import java.nio.charset.Charset

@Composable
fun MessageScreen(
    remoteUserId: String,
    remoteUsername: String,
    encodedRemoteUserProfilePictureUrl: String,
    imageLoader: ImageLoader,
    onNavigateUp: () -> Unit = {},
    onNavigate: (String) -> Unit = {},
    viewModel: MessageViewModel = hiltViewModel()
) {
    val decodeRemoteUserProfilePictureUrl = remember {
        encodedRemoteUserProfilePictureUrl.decodeBase64()?.string(Charset.defaultCharset())
    }
    val pagingState = viewModel.pagingState.value

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolbar(
            onNavigateUp = onNavigateUp,
            showBackArrow = true,
            title = {
                Image(
                    painter = rememberImagePainter(
                        data = decodeRemoteUserProfilePictureUrl,
                        imageLoader = imageLoader
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(ProfilePictureSizeSmall)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(spaceMedium))
                Text(
                    text = remoteUsername,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            }
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(spaceMedium)
            ) {
                items(pagingState.items.size) { i ->
                    val message = pagingState.items[i]
                    if(i>= pagingState.items.size - 1 && !pagingState.endReached && !pagingState.isLoading){
                        viewModel.loadNextMessages()
                    }
                    if(message.fromId == remoteUserId){
                        RemoteMessage(
                            message = message.text,
                            formattedTime = message.formattedTime,
                            color = MaterialTheme.colors.surface,
                            textColor = MaterialTheme.colors.onBackground
                        )
                        Spacer(modifier = Modifier.height(spaceMedium))
                    }else{
                        OwnMessage(
                            message = message.text,
                            formattedTime = message.formattedTime,
                            color = DarkerGreen,
                            textColor = MaterialTheme.colors.onBackground
                        )
                        Spacer(modifier = Modifier.height(spaceMedium))
                    }
                    Spacer(modifier = Modifier.height(spaceMedium))
                }
            }
            SendTextField(
                state = viewModel.messageTextFieldState.value,
                onValueChange = {
                    viewModel.onEvent(MessageEvent.EnteredMessage(it))
                },
                onSend = {
                    viewModel.onEvent(MessageEvent.SendMessage)
                },
                hint = stringResource(id = R.string.enter_a_message)
            )
        }
    }
}