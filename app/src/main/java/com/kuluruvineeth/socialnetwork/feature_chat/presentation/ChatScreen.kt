package com.kuluruvineeth.socialnetwork.presentation.chat

import android.util.Base64
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.items
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import coil.ImageLoader
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.spaceLarge
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.spaceMedium
import com.kuluruvineeth.socialnetwork.core.util.Screen
import com.kuluruvineeth.socialnetwork.feature_chat.domain.model.Chat
import com.kuluruvineeth.socialnetwork.feature_chat.presentation.chat.ChatItem
import com.kuluruvineeth.socialnetwork.feature_chat.presentation.chat.ChatViewModel
import com.kuluruvineeth.socialnetwork.presentation.components.StandardScaffold

@Composable
fun ChatScreen(
    imageLoader: ImageLoader,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: ChatViewModel = hiltViewModel()
) {
    val chats = viewModel.state.value.chats
    val isLoading = viewModel.state.value.isLoading
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spaceMedium),
        contentAlignment = Alignment.Center
    ){
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            items(chats){chat ->
                ChatItem(
                    item = chat,
                    imageLoader = imageLoader,
                    onItemClick = {
                        onNavigate(Screen.MessageScreen.route + "/${chat.remoteUserId}/${chat.remoteUsername}/${Base64.encodeToString(chat.remoteUserProfilePictureUrl.encodeToByteArray(), 0)}?chatId=${chat.chatId}")
                    }
                )
                Spacer(modifier = Modifier.height(spaceLarge))
            }
        }
    }
}