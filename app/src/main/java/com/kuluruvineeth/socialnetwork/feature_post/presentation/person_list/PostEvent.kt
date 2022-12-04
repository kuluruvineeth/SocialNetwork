package com.kuluruvineeth.socialnetwork.feature_post.presentation.person_list

import com.kuluruvineeth.socialnetwork.core.util.Event

sealed class PostEvent : Event(){
    object OnLiked: PostEvent()
}
