package com.kuluruvineeth.socialnetwork.feature_post.domain.repository

import android.net.Uri
import androidx.paging.PagingData
import com.kuluruvineeth.socialnetwork.core.domain.models.Post
import com.kuluruvineeth.socialnetwork.core.util.Constants
import com.kuluruvineeth.socialnetwork.core.util.Resource
import com.kuluruvineeth.socialnetwork.core.util.SimpleResource
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PostRepository {

    val posts: Flow<PagingData<Post>>

    suspend fun createPost(
        description: String,
        imageUri: Uri
    ): SimpleResource
}