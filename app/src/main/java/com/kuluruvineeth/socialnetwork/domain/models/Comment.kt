package com.kuluruvineeth.socialnetwork.domain.models

data class Comment(
    val username: String = "",
    val profilePictureUrl: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val comment: String = "",
    val commentId: Int = -1,
    val likeCount: Int = 12,
    val isLiked: Boolean = true
)
