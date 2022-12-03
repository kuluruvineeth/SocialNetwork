package com.kuluruvineeth.socialnetwork.feature_post.domain.use_case

import android.net.Uri
import com.kuluruvineeth.socialnetwork.core.util.SimpleResource
import com.kuluruvineeth.socialnetwork.feature_post.domain.repository.PostRepository
import java.io.File

class CreatePostUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(
        description: String,
        imageUri: Uri
    ): SimpleResource{
        return repository.createPost(description = description,imageUri)
    }
}