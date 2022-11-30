package com.kuluruvineeth.socialnetwork.di

import com.kuluruvineeth.socialnetwork.feature_auth.data.remote.AuthApi
import com.kuluruvineeth.socialnetwork.feature_auth.data.repository.AuthRepositoryImpl
import com.kuluruvineeth.socialnetwork.feature_auth.domain.repository.AuthRepository
import com.kuluruvineeth.socialnetwork.feature_auth.domain.use_case.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi{
        return Retrofit.Builder()
            .baseUrl(AuthApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApi): AuthRepository{
        return AuthRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(repository: AuthRepository): RegisterUseCase{
        return RegisterUseCase(repository)
    }
}