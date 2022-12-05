package com.kuluruvineeth.socialnetwork.feature_post.presentation.main_feed

sealed class MainFeedEvent{

    data class LikedPost(val postId: String): MainFeedEvent()
}
