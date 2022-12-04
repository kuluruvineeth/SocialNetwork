package com.kuluruvineeth.socialnetwork.feature_post.data.data_source.remote.request

data class CreateCommentRequest(
    val comment: String,
    val postId: String
)