package com.kuluruvineeth.socialnetwork.feature_auth.domain.repository

import com.kuluruvineeth.data.requests.CreateAccountRequest
import com.kuluruvineeth.socialnetwork.core.data.dto.response.BasicApiResponse
import com.kuluruvineeth.socialnetwork.core.util.Resource
import com.kuluruvineeth.socialnetwork.core.util.SimpleResource

interface AuthRepository {

    suspend fun register(
        email: String,
        username: String,
        password: String
    ): SimpleResource

    suspend fun login(
        email: String,
        password: String
    ): SimpleResource
}