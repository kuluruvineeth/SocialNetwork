package com.kuluruvineeth.socialnetwork.feature_post.data.repository

import com.kuluruvineeth.data.requests.CreateAccountRequest
import com.kuluruvineeth.socialnetwork.R
import com.kuluruvineeth.socialnetwork.core.domain.models.Post
import com.kuluruvineeth.socialnetwork.core.util.Resource
import com.kuluruvineeth.socialnetwork.core.util.UiText
import com.kuluruvineeth.socialnetwork.feature_post.data.data_source.remote.PostApi
import com.kuluruvineeth.socialnetwork.feature_post.domain.repository.PostRepository
import retrofit2.HttpException
import java.io.IOException

class PostRepositoryImpl(
    private val api: PostApi
) : PostRepository{
    override suspend fun getPostsForFollows(page: Int, pageSize: Int): Resource<List<Post>> {
        return try {
            val posts = api.getPostsForFollows(page,pageSize)
            Resource.Success(posts)
        }catch (e: IOException){
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )
        }catch (e: HttpException){
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }
}