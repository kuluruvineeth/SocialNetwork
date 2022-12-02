package com.kuluruvineeth.socialnetwork.feature_post.presentation.main_feed

import com.kuluruvineeth.socialnetwork.core.domain.models.Post

data class MainFeedState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val page: Int = 0,

)
