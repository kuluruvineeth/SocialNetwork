package com.kuluruvineeth.socialnetwork.feature_post.domain.use_case

import com.kuluruvineeth.socialnetwork.R
import com.kuluruvineeth.socialnetwork.core.util.Resource
import com.kuluruvineeth.socialnetwork.core.util.SimpleResource
import com.kuluruvineeth.socialnetwork.core.util.UiText
import com.kuluruvineeth.socialnetwork.feature_post.domain.repository.PostRepository

class CreateCommentUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(postId: String,comment: String): SimpleResource{
        if(comment.isBlank()){
            return Resource.Error(UiText.StringResource(R.string.error_field_empty))
        }
        if(postId.isBlank()){
            return Resource.Error(UiText.unknownError())
        }
        return repository.createComment(postId,comment)
    }
}