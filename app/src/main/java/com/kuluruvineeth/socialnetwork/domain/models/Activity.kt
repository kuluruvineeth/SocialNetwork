package com.kuluruvineeth.socialnetwork.domain.models

import com.kuluruvineeth.socialnetwork.domain.util.ActivityAction

data class Activity(
    val username: String,
    val actionType : ActivityAction,
    val formattedTime : String
)
