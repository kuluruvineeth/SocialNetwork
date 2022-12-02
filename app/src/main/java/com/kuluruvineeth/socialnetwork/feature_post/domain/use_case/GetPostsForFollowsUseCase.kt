package com.kuluruvineeth.socialnetwork.feature_post.domain.use_case

import com.kuluruvineeth.socialnetwork.core.domain.models.Post
import com.kuluruvineeth.socialnetwork.core.util.Resource
import com.kuluruvineeth.socialnetwork.feature_post.domain.repository.PostRepository

class GetPostsForFollowsUseCase(
    private val repository: PostRepository
) {
    suspend operator fun invoke(
        page:Int,
        pageSize:Int
    ): Resource<List<Post>>{
        return repository.getPostsForFollows(page,pageSize)
    }
}