package com.kuluruvineeth.socialnetwork.feature_chat.presentation.chat

import com.kuluruvineeth.socialnetwork.feature_chat.domain.model.Chat

data class ChatState(
    val chats: List<Chat> = emptyList(),
    val isLoading: Boolean = false
)
