package com.kuluruvineeth.socialnetwork.feature_activity.domain

sealed class ActivityAction{
    object LikedPost : ActivityAction()
    object CommentedOnPost : ActivityAction()
    object FollowedYou : ActivityAction()
}
