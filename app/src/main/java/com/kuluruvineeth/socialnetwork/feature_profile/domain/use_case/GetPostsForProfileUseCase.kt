package com.kuluruvineeth.socialnetwork.feature_profile.domain.use_case

import androidx.paging.PagingData
import com.kuluruvineeth.socialnetwork.core.domain.models.Post
import com.kuluruvineeth.socialnetwork.core.util.Resource
import com.kuluruvineeth.socialnetwork.feature_profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class GetPostsForProfileUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(userId: String,page:Int): Resource<List<Post>>{
        return repository.getPostsPaged(
            userId = userId,
            page = page
        )
    }
}