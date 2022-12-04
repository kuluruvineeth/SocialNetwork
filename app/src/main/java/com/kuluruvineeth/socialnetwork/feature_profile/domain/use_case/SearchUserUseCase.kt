package com.kuluruvineeth.socialnetwork.feature_profile.domain.use_case

import com.kuluruvineeth.socialnetwork.core.util.Resource
import com.kuluruvineeth.socialnetwork.feature_profile.domain.model.UserItem
import com.kuluruvineeth.socialnetwork.feature_profile.domain.repository.ProfileRepository
import retrofit2.http.Query

class SearchUserUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(query: String): Resource<List<UserItem>>{
        if(query.isBlank()){
            return Resource.Success(data = emptyList())
        }
        return repository.searchUser(query)
    }
}