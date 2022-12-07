package com.kuluruvineeth.socialnetwork.feature_chat.domain.use_case

import com.kuluruvineeth.socialnetwork.feature_chat.presentation.message.MessageEvent

data class ChatUseCases(
    val sendMessage: SendMessage,
    val observeChatEvents: ObserveChatEvents,
    val observeMessages: ObserveMessages,
    val getChatsForUser: GetChatsForUser,
    val getMessagesForChat: GetMessagesForChat
)
