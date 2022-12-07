package com.kuluruvineeth.socialnetwork.feature_post.presentation.main_feed

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kuluruvineeth.socialnetwork.R
import com.kuluruvineeth.socialnetwork.core.domain.models.Post
import com.kuluruvineeth.socialnetwork.core.domain.util.ParentType
import com.kuluruvineeth.socialnetwork.core.presentation.PagingState
import com.kuluruvineeth.socialnetwork.core.presentation.util.UiEvent
import com.kuluruvineeth.socialnetwork.core.util.*
import com.kuluruvineeth.socialnetwork.feature_post.data.paging.PostSource
import com.kuluruvineeth.socialnetwork.feature_post.domain.use_case.GetPostsForFollowsUseCase
import com.kuluruvineeth.socialnetwork.feature_post.domain.use_case.PostUseCases
import com.kuluruvineeth.socialnetwork.feature_post.presentation.person_list.PostEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFeedViewModel @Inject constructor(
    private val postUseCases: PostUseCases,
    private val postLiker: PostLiker
) : ViewModel(){



    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _pagingState = mutableStateOf<PagingState<Post>>(PagingState())
    val pagingState: State<PagingState<Post>> = _pagingState

    private val paginator = DefaultPaginator(
        onLoadUpdated = {isLoading ->
            _pagingState.value = pagingState.value.copy(
                isLoading = isLoading
            )
        },
        onRequest = {page ->
            postUseCases.getPostsForFollows(page = page)
        },
        onSuccess = {posts ->
            _pagingState.value = pagingState.value.copy(
                items = pagingState.value.items + posts,
                endReached = posts.isEmpty(),
                isLoading = false
            )
        },
        onError = {uiText ->
            _eventFlow.emit(UiEvent.ShowSnackbar(uiText))
        }
    )

    init {
        loadNextPosts()
    }

    fun onEvent(event: MainFeedEvent){
        when(event){
            is MainFeedEvent.LikedPost -> {
                toggleLikeForParent(event.postId)
            }
            is MainFeedEvent.DeletePost -> {
                deletePost(event.post.id)
            }
        }
    }

    private fun deletePost(postId: String) {
        viewModelScope.launch {
            when (val result = postUseCases.deletePost(postId)) {
                is Resource.Success -> {
                    _pagingState.value = pagingState.value.copy(
                        items = pagingState.value.items.filter {
                            it.id != postId
                        }
                    )
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            UiText.StringResource(
                                R.string.successfully_deleted_post
                            )
                        )
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(result.uiText ?: UiText.unknownError())
                    )
                }
            }
        }
    }

    fun loadNextPosts(){
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    private fun toggleLikeForParent(
        parentId: String
    ){
        viewModelScope.launch {
            postLiker.toggleLike(
                posts = pagingState.value.items,
                parentId = parentId,
                onRequest = {isLiked ->
                    postUseCases.toggleLikeForParent(
                        parentId = parentId,
                        parentType = ParentType.Post.type,
                        isLiked = isLiked
                    )
                },
                onStateUpdated = {posts ->
                    _pagingState.value = pagingState.value.copy(
                        items = posts
                    )
                }
            )
        }
    }
}