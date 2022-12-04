package com.kuluruvineeth.socialnetwork.feature_activity.presentation.activity

sealed class ActivityEvent{
    data class ClickedOnUser(val userId: String): ActivityEvent()
    data class ClickedOnParent(val parentId: String): ActivityEvent()
}
