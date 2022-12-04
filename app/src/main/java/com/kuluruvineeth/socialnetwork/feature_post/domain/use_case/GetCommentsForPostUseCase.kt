package com.kuluruvineeth.socialnetwork.feature_post.domain.use_case

import com.kuluruvineeth.socialnetwork.core.domain.models.Comment
import com.kuluruvineeth.socialnetwork.core.util.Resource
import com.kuluruvineeth.socialnetwork.feature_post.domain.repository.PostRepository

class GetCommentsForPostUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(postId: String): Resource<List<Comment>>{
        return repository.getCommentsForPost(postId)
    }
}