package com.kuluruvineeth.socialnetwork.feature_auth.domain.use_case

import com.kuluruvineeth.socialnetwork.core.util.SimpleResource
import com.kuluruvineeth.socialnetwork.feature_auth.domain.repository.AuthRepository

class RegisterUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        email: String,
        username: String,
        password: String
    ): SimpleResource{
        return repository.register(email.trim(),username.trim(),password.trim())
    }
}