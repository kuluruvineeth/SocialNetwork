package com.kuluruvineeth.socialnetwork.feature_activity.domain.repository

import androidx.paging.PagingData
import com.kuluruvineeth.socialnetwork.core.domain.models.Activity
import kotlinx.coroutines.flow.Flow

interface ActivityRepository {

    val activities: Flow<PagingData<Activity>>
}