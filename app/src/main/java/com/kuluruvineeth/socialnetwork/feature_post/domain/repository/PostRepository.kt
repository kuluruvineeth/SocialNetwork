package com.kuluruvineeth.socialnetwork.feature_post.domain.repository

import com.kuluruvineeth.socialnetwork.core.domain.models.Post
import com.kuluruvineeth.socialnetwork.core.util.Constants
import com.kuluruvineeth.socialnetwork.core.util.Resource

interface PostRepository {

    suspend fun getPostsForFollows(
        page: Int = 0,
        pageSize: Int = Constants.PAGE_SIZE_POSTS
    ): Resource<List<Post>>
}