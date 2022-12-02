package com.kuluruvineeth.socialnetwork.feature_post.presentation.main_feed

import com.kuluruvineeth.socialnetwork.core.domain.models.Post

data class MainFeedState(
    val isLoadingFirstTime: Boolean = true,
    val isLoadingNewPosts: Boolean = false,
)
