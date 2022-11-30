package com.kuluruvineeth.socialnetwork.feature_auth.data.remote

import com.kuluruvineeth.data.requests.CreateAccountRequest
import com.kuluruvineeth.socialnetwork.core.data.dto.response.BasicApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/user/create")
    suspend fun register(
        @Body request: CreateAccountRequest
    ): BasicApiResponse

    companion object{
        const val BASE_URL = "http://10.0.2.2:8001/"
    }

}