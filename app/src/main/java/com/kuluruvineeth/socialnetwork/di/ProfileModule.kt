package com.kuluruvineeth.socialnetwork.di

import android.content.SharedPreferences
import com.google.gson.Gson
import com.kuluruvineeth.socialnetwork.feature_post.data.data_source.remote.PostApi
import com.kuluruvineeth.socialnetwork.feature_profile.data.remote.ProfileApi
import com.kuluruvineeth.socialnetwork.feature_profile.data.repository.ProfileRepositoryImpl
import com.kuluruvineeth.socialnetwork.feature_profile.domain.repository.ProfileRepository
import com.kuluruvineeth.socialnetwork.feature_profile.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun provideProfileApi(client: OkHttpClient): ProfileApi{
        return Retrofit.Builder()
            .baseUrl(ProfileApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ProfileApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileRepostory(profileApi: ProfileApi,postApi: PostApi,gson: Gson,sharedPreferences: SharedPreferences): ProfileRepository {
        return ProfileRepositoryImpl(profileApi,postApi,gson,sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideToggleFollowForUserUseCase(repository: ProfileRepository): ToggleFollowStateForUserUseCase{
        return ToggleFollowStateForUserUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideProfileUseCases(repository: ProfileRepository): ProfileUseCases{
        return ProfileUseCases(
            getProfile = GetProfileUseCase(repository),
            getSkills = GetSkillsUseCase(repository),
            updateProfile = UpdateProfileUseCase(repository),
            setSkillSelected = SetSkillSelectedUseCase(),
            getPostsForProfile = GetPostsForProfileUseCase(repository),
            searchUser = SearchUserUseCase(repository),
            toggleFollowStateForUser = ToggleFollowStateForUserUseCase(repository),
            logout = LogoutUseCase(repository)
        )
    }
}