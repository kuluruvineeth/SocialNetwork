package com.kuluruvineeth.socialnetwork.feature_profile.domain.use_case

import com.kuluruvineeth.socialnetwork.core.util.Resource
import com.kuluruvineeth.socialnetwork.feature_profile.domain.model.Profile
import com.kuluruvineeth.socialnetwork.feature_profile.domain.repository.ProfileRepository

class GetProfileUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(userId: String): Resource<Profile>{
        return repository.getProfile(userId)
    }
}