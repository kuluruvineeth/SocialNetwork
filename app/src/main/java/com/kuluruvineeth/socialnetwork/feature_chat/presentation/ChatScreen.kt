package com.kuluruvineeth.socialnetwork.presentation.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.items
import coil.ImageLoader
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.spaceLarge
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.spaceMedium
import com.kuluruvineeth.socialnetwork.core.util.Screen
import com.kuluruvineeth.socialnetwork.feature_chat.domain.model.Chat
import com.kuluruvineeth.socialnetwork.feature_chat.presentation.chat.ChatItem
import com.kuluruvineeth.socialnetwork.presentation.components.StandardScaffold

@Composable
fun ChatScreen(
    imageLoader: ImageLoader,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {}
) {
    val chats = remember {
        listOf(
            Chat(
                remoteUsername = "Kuluru Vineeth",
                remoteUserProfileUrl = "http://192.168.0.2:8001/profile_pictures/philipp.jpg",
                lastMessage = "This is the last message of the chat with Philipp",
                lastMessageFormattedTime = "19:39"
            ),
            Chat(
                remoteUsername = "Florian",
                remoteUserProfileUrl = "http://192.168.0.2:8001/profile_pictures/philipp.jpg",
                lastMessage = "This is the last message of the chat with Philipp",
                lastMessageFormattedTime = "19:39"
            ),
            Chat(
                remoteUsername = "Kuluru Vineeth",
                remoteUserProfileUrl = "http://192.168.0.2:8001/profile_pictures/philipp.jpg",
                lastMessage = "This is the last message of the chat with Philipp",
                lastMessageFormattedTime = "19:39"
            ),
        )
    }
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
                        onNavigate(Screen.MessageScreen.route)
                    }
                )
                Spacer(modifier = Modifier.height(spaceLarge))
            }
        }
    }
}