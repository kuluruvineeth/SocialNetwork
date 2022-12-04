package com.kuluruvineeth.socialnetwork.feature_auth.presentation.util

sealed class AuthError : com.kuluruvineeth.socialnetwork.core.util.Error(){
    object FieldEmpty : AuthError()
    object InputTooShort : AuthError()
    object InvalidEmail : AuthError()
    object InvalidPassword : AuthError()
}
