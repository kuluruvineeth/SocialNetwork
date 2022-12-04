package com.kuluruvineeth.socialnetwork.feature_post.presentation.util


sealed class PostDescriptionError : com.kuluruvineeth.socialnetwork.core.util.Error(){
    object FieldEmpty : PostDescriptionError()
}