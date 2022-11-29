package com.kuluruvineeth.socialnetwork.core.domain.models

import com.kuluruvineeth.socialnetwork.feature_activity.domain.ActivityAction

data class Activity(
    val username: String,
    val actionType : ActivityAction,
    val formattedTime : String
)
