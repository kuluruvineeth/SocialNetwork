package com.kuluruvineeth.socialnetwork.feature_profile.data.remote

import com.kuluruvineeth.socialnetwork.core.data.dto.response.BasicApiResponse
import com.kuluruvineeth.socialnetwork.feature_profile.data.remote.request.FollowUpdateRequest
import com.kuluruvineeth.socialnetwork.feature_profile.data.remote.response.ProfileResponse
import com.kuluruvineeth.socialnetwork.feature_profile.data.remote.response.SkillDto
import com.kuluruvineeth.socialnetwork.feature_profile.data.remote.response.UserItemDto
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface ProfileApi {

    @GET("/api/user/profile")
    suspend fun getProfile(
        @Query("userId") userId: String
    ): BasicApiResponse<ProfileResponse>

    @Multipart
    @PUT("/api/user/update")
    suspend fun updateProfile(
        @Part bannerImage: MultipartBody.Part?,
        @Part profilePicture: MultipartBody.Part?,
        @Part updateProfileData: MultipartBody.Part
    ): BasicApiResponse<Unit>

    @GET("/api/skills/get")
    suspend fun getSkills(): List<SkillDto>

    @GET("/api/user/search")
    suspend fun searchUser(
        @Query("query") query: String
    ): List<UserItemDto>

    @POST("/api/following/follow")
    suspend fun followUser(
        @Body request: FollowUpdateRequest
    ): BasicApiResponse<Unit>

    @DELETE("/api/following/unfollow")
    suspend fun unfollowUser(
        @Query("userId") userId: String
    ): BasicApiResponse<Unit>

    companion object{
        const val BASE_URL = "http://10.0.2.2:8001/"
    }
}