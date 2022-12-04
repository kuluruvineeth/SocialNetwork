package com.kuluruvineeth.socialnetwork.feature_profile.presentation.util

sealed class EditProfileError : com.kuluruvineeth.socialnetwork.core.util.Error(){
    object FieldEmpty : EditProfileError()
}