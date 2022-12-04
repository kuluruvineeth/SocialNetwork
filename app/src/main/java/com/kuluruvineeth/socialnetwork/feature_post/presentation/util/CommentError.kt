package com.kuluruvineeth.socialnetwork.feature_post.presentation.util

sealed class CommentError: com.kuluruvineeth.socialnetwork.core.util.Error(){
    object FieldEmpty: CommentError()
}
