package com.kuluruvineeth.socialnetwork.feature_chat.data.remote.data

import com.kuluruvineeth.socialnetwork.feature_chat.domain.model.Message
import java.text.DateFormat
import java.util.*

data class MessageDto(
    val fromId: String,
    val toId: String,
    val text: String,
    val timestamp: Long,
    val chatId: String?,
    val id: String
){
    fun toMessage(): Message{
        return Message(
            fromId = fromId,
            toId = toId,
            text = text,
            formattedTime = DateFormat
                .getDateInstance(DateFormat.DEFAULT)
                .format(Date(timestamp)),
            chatId = chatId
        )
    }
}
