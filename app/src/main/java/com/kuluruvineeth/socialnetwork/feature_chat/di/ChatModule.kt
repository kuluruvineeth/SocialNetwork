package com.kuluruvineeth.socialnetwork.feature_chat.di

import android.app.Application
import com.google.gson.Gson
import com.kuluruvineeth.socialnetwork.core.util.Constants
import com.kuluruvineeth.socialnetwork.feature_chat.data.remote.ChatApi
import com.kuluruvineeth.socialnetwork.feature_chat.data.remote.ChatService
import com.kuluruvineeth.socialnetwork.feature_chat.data.remote.util.CustomGsonMessageAdapter
import com.kuluruvineeth.socialnetwork.feature_chat.data.remote.util.FlowStreamAdapter
import com.kuluruvineeth.socialnetwork.feature_chat.data.repository.ChatRepositoryImpl
import com.kuluruvineeth.socialnetwork.feature_chat.domain.repository.ChatRepository
import com.kuluruvineeth.socialnetwork.feature_chat.domain.use_case.*
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.retry.LinearBackoffStrategy
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import com.tinder.streamadapter.coroutines.CoroutinesStreamAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object ChatModule {

    @Provides
    fun provideChatUseCases(repository: ChatRepository): ChatUseCases {
        return ChatUseCases(
            sendMessage = SendMessage(repository),
            observeChatEvents = ObserveChatEvents(repository),
            observeMessages = ObserveMessages(repository),
            getChatsForUser = GetChatsForUser(repository),
            getMessagesForChat = GetMessagesForChat(repository),
            initializeRepository = InitializeRepository(repository)
        )
    }

    @Provides
    @Singleton
    fun provideChatApi(client: OkHttpClient): ChatApi {
        return Retrofit.Builder()
            .baseUrl(ChatApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create()
    }

    @Provides
    fun provideChatRepository(client: OkHttpClient, chatApi: ChatApi): ChatRepository {
        return ChatRepositoryImpl(chatApi,client)
    }
}