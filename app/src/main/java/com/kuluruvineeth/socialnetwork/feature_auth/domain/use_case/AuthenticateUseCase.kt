package com.kuluruvineeth.socialnetwork.feature_auth.domain.use_case

import com.kuluruvineeth.socialnetwork.core.util.SimpleResource
import com.kuluruvineeth.socialnetwork.feature_auth.domain.repository.AuthRepository

class AuthenticateUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): SimpleResource{
        return repository.authenticate()
    }
}