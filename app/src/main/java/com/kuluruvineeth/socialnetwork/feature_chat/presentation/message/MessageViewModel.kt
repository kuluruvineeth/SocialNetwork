package com.kuluruvineeth.socialnetwork.feature_chat.presentation.message

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuluruvineeth.socialnetwork.core.domain.states.StandardTextFieldState
import com.kuluruvineeth.socialnetwork.core.presentation.PagingState
import com.kuluruvineeth.socialnetwork.core.presentation.util.UiEvent
import com.kuluruvineeth.socialnetwork.core.util.DefaultPaginator
import com.kuluruvineeth.socialnetwork.core.util.Resource
import com.kuluruvineeth.socialnetwork.core.util.UiText
import com.kuluruvineeth.socialnetwork.feature_chat.domain.model.Message
import com.kuluruvineeth.socialnetwork.feature_chat.domain.use_case.ChatUseCases
import com.tinder.scarlet.WebSocket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val chatUseCases: ChatUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _messageTextFieldState = mutableStateOf(StandardTextFieldState())
    val messageTextFieldState: State<StandardTextFieldState> = _messageTextFieldState

    private val _pagingState = mutableStateOf<PagingState<Message>>(PagingState())
    val pagingState: State<PagingState<Message>> = _pagingState

    private val _state = mutableStateOf(MessageState())
    val state: State<MessageState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val paginator = DefaultPaginator(
        onLoadUpdated = {isLoading ->
            _pagingState.value = pagingState.value.copy(isLoading = isLoading)
        },
        onRequest = {nextPage ->
            savedStateHandle.get<String>("chatId")?.let { chatId ->
                chatUseCases.getMessagesForChat(
                    chatId,nextPage
                )
            } ?: Resource.Error(UiText.unknownError())
        },
        onError = {errorUiText ->
            _eventFlow.emit(UiEvent.ShowSnackbar(errorUiText))
        },
        onSuccess = {messages ->
            _pagingState.value = pagingState.value.copy(
                items = pagingState.value.items + messages,
                endReached = messages.isEmpty(),
                isLoading = false
            )
        }
    )

    init {
        chatUseCases.initializeRepository()
        loadNextMessages()
        observeChatEvents()
        observeChatMessages()
    }

    private fun observeChatMessages(){
        viewModelScope.launch {
            chatUseCases.observeMessages()
                .collect { message ->
                    println("Message received: $message")
                    _state.value = state.value.copy(
                        messages = state.value.messages + message
                    )
                }
        }

    }

    private fun observeChatEvents(){
        chatUseCases.observeChatEvents()
            .onEach { event ->
                when(event){
                    is WebSocket.Event.OnConnectionOpened<*> -> {
                        println("Connection was openend")
                    }
                    is WebSocket.Event.OnConnectionFailed -> {
                        println("Connection failed: ${event.throwable}")
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun loadNextMessages(){
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    private fun sendMessage(){
        val toId = savedStateHandle.get<String>("remoteUserId") ?: return
        if(messageTextFieldState.value.text.isBlank()){
            return
        }
        val chatId = savedStateHandle.get<String>("chatId")
        chatUseCases.sendMessage(toId,messageTextFieldState.value.text,chatId)
    }

    fun onEvent(event: MessageEvent){
        when(event){
            is MessageEvent.EnteredMessage -> {
                _messageTextFieldState.value = messageTextFieldState.value.copy(
                    text = event.message
                )
            }
            is MessageEvent.SendMessage -> {
                sendMessage()
            }
        }
    }
}