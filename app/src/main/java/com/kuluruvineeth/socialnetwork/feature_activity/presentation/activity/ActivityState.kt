package com.kuluruvineeth.socialnetwork.feature_activity.presentation.activity

import com.kuluruvineeth.socialnetwork.core.domain.models.Activity

data class ActivityState(
    val activities: List<Activity> = emptyList(),
    val isLoading: Boolean = false
)
