package com.kuluruvineeth.socialnetwork.feature_profile.domain.repository

import android.net.Uri
import androidx.paging.Pager
import androidx.paging.PagingData
import com.kuluruvineeth.socialnetwork.core.domain.models.Post
import com.kuluruvineeth.socialnetwork.core.util.Constants
import com.kuluruvineeth.socialnetwork.core.util.Resource
import com.kuluruvineeth.socialnetwork.core.util.SimpleResource
import com.kuluruvineeth.socialnetwork.feature_profile.domain.model.Profile
import com.kuluruvineeth.socialnetwork.feature_profile.domain.model.Skill
import com.kuluruvineeth.socialnetwork.feature_profile.domain.model.UpdateProfileData
import com.kuluruvineeth.socialnetwork.feature_profile.domain.model.UserItem
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun getProfile(userId: String): Resource<Profile>

    suspend fun updateProfile(
        updateProfileData: UpdateProfileData,
        bannerImageUri: Uri?,
        profilePictureUri: Uri?
    ): SimpleResource

    suspend fun getSkills(): Resource<List<Skill>>

    suspend fun searchUser(query: String): Resource<List<UserItem>>

    suspend fun followUser(userId: String): SimpleResource

    suspend fun unfollowUser(userId: String): SimpleResource

    suspend fun getPostsPaged(
        page: Int = 0,
        pageSize: Int = Constants.DEFAULT_PAGE_SIZE,
        userId: String
    ): Resource<List<Post>>

    fun logout()
}