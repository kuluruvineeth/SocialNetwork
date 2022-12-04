package com.kuluruvineeth.socialnetwork.feature_post.domain.use_case

import com.kuluruvineeth.socialnetwork.core.util.Resource
import com.kuluruvineeth.socialnetwork.feature_post.domain.repository.PostRepository
import com.kuluruvineeth.socialnetwork.feature_profile.domain.model.UserItem

class GetLikesForParentUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(parentId:String): Resource<List<UserItem>>{
        return repository.getLikesForParent(parentId)
    }
}