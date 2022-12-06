package com.kuluruvineeth.socialnetwork.feature_chat.data.repository

import com.kuluruvineeth.socialnetwork.R
import com.kuluruvineeth.socialnetwork.core.util.Resource
import com.kuluruvineeth.socialnetwork.core.util.UiText
import com.kuluruvineeth.socialnetwork.feature_chat.data.remote.ChatApi
import com.kuluruvineeth.socialnetwork.feature_chat.data.remote.ChatService
import com.kuluruvineeth.socialnetwork.feature_chat.data.remote.data.WsClientMessage
import com.kuluruvineeth.socialnetwork.feature_chat.domain.model.Chat
import com.kuluruvineeth.socialnetwork.feature_chat.domain.model.Message
import com.kuluruvineeth.socialnetwork.feature_chat.domain.repository.ChatRepository
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException

class ChatRepositoryImpl(
    private val chatService: ChatService,
    private val chatApi: ChatApi
): ChatRepository {

    override suspend fun getChatsForUser(): Resource<List<Chat>> {
        return try {
            val chats = chatApi
                .getChatsForUser()
                .mapNotNull { it.toChat() }
            println(chats)
            Resource.Success(data = chats)
        } catch(e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        } catch(e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }

    override fun observeChatEvents(): Flow<WebSocket.Event> {
        return chatService.observeEvents()
    }

    override fun observeMessages(): Flow<Message> {
        return chatService
            .observeMessages()
            .map { it.toMessage() }
    }

    override fun sendMessage(toId: String, text: String, chatId: String?) {
        chatService.sendMessage(
            WsClientMessage(
                toId = toId,
                text = text,
                chatId = chatId
            )
        )
    }
}