package com.kuluruvineeth.socialnetwork.feature_auth.presentation.login

sealed class LoginEvent{

    data class EnteredEmail(val email: String): LoginEvent()
    data class EnteredPassword(val password: String): LoginEvent()
    object Login : LoginEvent()
    object TogglePassswordVisibility : LoginEvent()
}
