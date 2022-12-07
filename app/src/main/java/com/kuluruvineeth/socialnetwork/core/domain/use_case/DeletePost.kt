package com.kuluruvineeth.socialnetwork.core.domain.use_case

import com.kuluruvineeth.socialnetwork.core.util.SimpleResource
import com.kuluruvineeth.socialnetwork.feature_post.domain.repository.PostRepository

class DeletePost(
    private val repository: PostRepository
) {

    suspend operator fun invoke(postId: String): SimpleResource{
        return repository.deletePost(postId)
    }
}