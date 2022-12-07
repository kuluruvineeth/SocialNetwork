package com.kuluruvineeth.socialnetwork.di

import android.content.Context
import com.google.gson.Gson
import com.kuluruvineeth.socialnetwork.core.domain.use_case.DeletePost
import com.kuluruvineeth.socialnetwork.feature_post.data.data_source.remote.PostApi
import com.kuluruvineeth.socialnetwork.feature_post.data.repository.PostRepositoryImpl
import com.kuluruvineeth.socialnetwork.feature_post.domain.repository.PostRepository
import com.kuluruvineeth.socialnetwork.feature_post.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostModule {

    @Provides
    @Singleton
    fun providePostApi(client: OkHttpClient): PostApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(PostApi.BASE_URL)
            .client(client)
            .build()
            .create(PostApi::class.java)
    }

    @Provides
    @Singleton
    fun providePostRepository(
        api: PostApi,
        gson:Gson
    ): PostRepository{
        return PostRepositoryImpl(api,gson)
    }


    @Provides
    @Singleton
    fun providePostUseCases(repository: PostRepository): PostUseCases{
        return PostUseCases(
            getPostsForFollows = GetPostsForFollowsUseCase(repository = repository),
            createPostUseCase = CreatePostUseCase(repository),
            getPostDetails = GetPostDetailsUseCase(repository),
            getCommentsForPost = GetCommentsForPostUseCase(repository),
            createComment = CreateCommentUseCase(repository),
            toggleLikeForParent = ToggleLikeForParentUseCase(repository),
            getLikesForParent = GetLikesForParentUseCase(repository),
            deletePost = DeletePost(repository)
        )
    }
}