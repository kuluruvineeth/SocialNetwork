package com.kuluruvineeth.socialnetwork.feature_profile.presentation.search

import com.kuluruvineeth.socialnetwork.core.util.Error
import com.kuluruvineeth.socialnetwork.core.util.UiText

data class SearchError(
    val message: UiText
): Error()
