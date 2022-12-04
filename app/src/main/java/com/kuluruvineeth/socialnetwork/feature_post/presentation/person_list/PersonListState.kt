package com.kuluruvineeth.socialnetwork.feature_post.presentation.person_list

import com.kuluruvineeth.socialnetwork.feature_profile.domain.model.UserItem

data class PersonListState(
    val users: List<UserItem> = emptyList(),
    val isLoading: Boolean = false
)
