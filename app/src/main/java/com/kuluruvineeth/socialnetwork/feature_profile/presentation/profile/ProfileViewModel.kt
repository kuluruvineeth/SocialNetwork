package com.kuluruvineeth.socialnetwork.feature_profile.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kuluruvineeth.socialnetwork.core.domain.models.Post
import com.kuluruvineeth.socialnetwork.core.domain.use_case.GetOwnUserIdUseCase
import com.kuluruvineeth.socialnetwork.core.domain.util.ParentType
import com.kuluruvineeth.socialnetwork.core.presentation.PagingState
import com.kuluruvineeth.socialnetwork.core.presentation.util.UiEvent
import com.kuluruvineeth.socialnetwork.core.util.Event
import com.kuluruvineeth.socialnetwork.core.util.Resource
import com.kuluruvineeth.socialnetwork.core.util.UiText
import com.kuluruvineeth.socialnetwork.feature_post.domain.use_case.PostUseCases
import com.kuluruvineeth.socialnetwork.feature_post.presentation.person_list.PostEvent
import com.kuluruvineeth.socialnetwork.feature_profile.domain.use_case.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val postUseCases: PostUseCases,
    private val getOwnUserId: GetOwnUserIdUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _toolbarState = mutableStateOf<ProfileToolbarState>(ProfileToolbarState())
    val toolbarState : State<ProfileToolbarState> = _toolbarState

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var page = 0
    private val _pagingState = mutableStateOf<PagingState<Post>>(PagingState())
    val pagingState: State<PagingState<Post>> = _pagingState

    fun setExpandedRatio(ratio: Float){
        _toolbarState.value = _toolbarState.value.copy(
            expandedRatio = ratio
        )
    }

    fun setToolbarOffsetY(value: Float){
        _toolbarState.value = _toolbarState.value.copy(
            toolbarOffsetY = value
        )
    }

    init {
        loadNextPosts()
    }

    fun onEvent(event: ProfileEvent){
        when(event){
            is ProfileEvent.GetProfile -> {

            }
            is ProfileEvent.LikePost -> {
                viewModelScope.launch {
                    toggleLikeForParent(
                        parentId = event.postId
                    )
                }
            }
        }
    }

    fun loadNextPosts(){
        viewModelScope.launch {
            _pagingState.value = pagingState.value.copy(
                isLoading = true
            )
            val userId = savedStateHandle.get<String>("userId") ?: getOwnUserId()
            val result = profileUseCases.getPostsForProfile(
                userId = userId,
                page = page
            )
            when(result){
                is Resource.Success -> {
                    val posts = result.data ?: emptyList()
                    _pagingState.value = pagingState.value.copy(
                        items = pagingState.value.items + posts,
                        endReached = posts.isEmpty(),
                        isLoading = false
                    )
                    page++
                    Timber.d("Paging state changed to ${pagingState.value}")
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(result.uiText ?: UiText.unknownError())
                    )
                    _pagingState.value = pagingState.value.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun toggleLikeForParent(
        parentId: String
    ) {
        viewModelScope.launch {
            val post = pagingState.value.items.find { it.id == parentId }
            val currentlyLiked = post?.isLiked == true
            val currentLikeCount = post?.likeCount ?: 0
            val newPosts = pagingState.value.items.map { post ->
                if(post.id == parentId){
                    post.copy(
                        isLiked = !post.isLiked,
                        likeCount = if(currentlyLiked){
                            post.likeCount - 1
                        }else post.likeCount + 1
                    )
                }else post
            }
            val result = postUseCases.toggleLikeForParent(
                parentId = parentId,
                parentType = ParentType.Post.type,
                isLiked = currentlyLiked
            )
            _pagingState.value = pagingState.value.copy(
                items = newPosts
            )
            when(result) {
                is Resource.Success -> Unit
                is Resource.Error -> {
                    val oldPosts = pagingState.value.items.map { post ->
                        if(post.id == parentId){
                            post.copy(
                                isLiked = currentlyLiked,
                                likeCount = currentLikeCount
                            )
                        }else post
                    }
                    _pagingState.value = pagingState.value.copy(
                        items = oldPosts
                    )
                }
            }
        }
    }

    fun getProfile(userId: String?){
        viewModelScope.launch {
            _state.value = state.value.copy(
                isLoading = true
            )
            val result = profileUseCases.getProfile(
                userId ?: getOwnUserId()
            )
            when(result){
                is Resource.Success -> {
                    _state.value = state.value.copy(
                        profile = result.data,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false
                    )
                    _eventFlow.emit(UiEvent.ShowSnackbar(
                        uiText = result.uiText ?: UiText.unknownError()
                    ))
                }
            }
        }
    }
}