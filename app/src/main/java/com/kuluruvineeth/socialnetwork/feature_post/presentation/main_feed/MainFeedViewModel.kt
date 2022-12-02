package com.kuluruvineeth.socialnetwork.feature_post.presentation.main_feed

import androidx.lifecycle.ViewModel
import com.kuluruvineeth.socialnetwork.feature_post.domain.use_case.GetPostsForFollowsUseCase
import com.kuluruvineeth.socialnetwork.feature_post.domain.use_case.PostUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainFeedViewModel @Inject constructor(
    private val postUseCases: PostUseCases
) : ViewModel(){


}