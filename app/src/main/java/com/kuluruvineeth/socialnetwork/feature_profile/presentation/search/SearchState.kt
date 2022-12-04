package com.kuluruvineeth.socialnetwork.feature_profile.presentation.search

import com.kuluruvineeth.socialnetwork.feature_profile.domain.model.UserItem

data class SearchState(
    val userItems: List<UserItem> = emptyList(),
    val isLoading: Boolean = false
)
