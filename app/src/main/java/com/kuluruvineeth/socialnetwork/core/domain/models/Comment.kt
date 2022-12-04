package com.kuluruvineeth.socialnetwork.core.domain.models

data class Comment(
    val id: String,
    val username: String,
    val profilePictureUrl: String,
    val formattedTime: String,
    val comment: String,
    val likeCount: Int,
    val isLiked: Boolean
)
