package com.kuluruvineeth.socialnetwork.feature_chat.domain.use_case

import com.kuluruvineeth.socialnetwork.feature_chat.domain.repository.ChatRepository

class InitializeRepository(
    private val repository: ChatRepository
) {

    operator fun invoke(){
        repository.initialize()
    }
}