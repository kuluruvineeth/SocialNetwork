package com.kuluruvineeth.socialnetwork.feature_chat.domain.model

data class Chat(
    val remoteUsername: String,
    val remoteUserProfileUrl: String,
    val lastMessage: String,
    val lastMessageFormattedTime: String
)
