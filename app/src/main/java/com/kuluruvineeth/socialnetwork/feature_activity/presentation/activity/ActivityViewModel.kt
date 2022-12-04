package com.kuluruvineeth.socialnetwork.feature_activity.presentation.activity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kuluruvineeth.socialnetwork.feature_activity.domain.use_case.GetActivitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val getActivities: GetActivitiesUseCase
) : ViewModel(){

    val activities = getActivities().cachedIn(viewModelScope)

    private val _state = mutableStateOf(ActivityState())
    val state: State<ActivityState> = _state

    fun onEvent(event: ActivityEvent){
        when(event){
            is ActivityEvent.ClickedOnUser -> {

            }
            is ActivityEvent.ClickedOnParent -> {

            }
        }
    }

}