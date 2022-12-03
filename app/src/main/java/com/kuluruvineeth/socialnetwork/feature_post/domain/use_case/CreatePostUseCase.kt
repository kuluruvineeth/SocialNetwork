package com.kuluruvineeth.socialnetwork.feature_post.domain.use_case

import android.net.Uri
import com.kuluruvineeth.socialnetwork.R
import com.kuluruvineeth.socialnetwork.core.util.Resource
import com.kuluruvineeth.socialnetwork.core.util.SimpleResource
import com.kuluruvineeth.socialnetwork.core.util.UiText
import com.kuluruvineeth.socialnetwork.feature_post.domain.repository.PostRepository
import java.io.File

class CreatePostUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(
        description: String,
        imageUri: Uri?
    ): SimpleResource{
        if(imageUri == null){
            return Resource.Error(
                return Resource.Error(
                    uiText = UiText.StringResource(R.string.error_no_image_picked)
                )
            )
        }
        if(description.isBlank()){
            return Resource.Error(
                uiText = UiText.StringResource(R.string.error_description_blank)
            )
        }
        return repository.createPost(description = description,imageUri)
    }
}