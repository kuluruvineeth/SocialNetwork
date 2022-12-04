package com.kuluruvineeth.socialnetwork.core.domain.states

data class PasswordTextFieldState(
    val text: String = "",
    val error: com.kuluruvineeth.socialnetwork.core.util.Error? = null,
    val isPasswordVisible: Boolean = false
)
