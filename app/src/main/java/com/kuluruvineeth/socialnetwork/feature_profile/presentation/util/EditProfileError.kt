package com.kuluruvineeth.socialnetwork.feature_profile.presentation.util

sealed class EditProfileError : Error(){
    object FieldEmpty : EditProfileError()
}