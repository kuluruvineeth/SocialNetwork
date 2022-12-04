package com.kuluruvineeth.socialnetwork.feature_activity.presentation.activity

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.paging.compose.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.kuluruvineeth.socialnetwork.R
import com.kuluruvineeth.socialnetwork.core.domain.models.Activity
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.spaceMedium
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.spaceSmall
import com.kuluruvineeth.socialnetwork.feature_activity.domain.ActivityType
import com.kuluruvineeth.socialnetwork.core.util.DateFormatUtil
import com.kuluruvineeth.socialnetwork.presentation.components.StandardToolbar
import kotlin.random.Random

@Composable
fun ActivityScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: ActivityViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val activities = viewModel.activities.collectAsLazyPagingItems()
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            StandardToolbar(
                onNavigateUp = onNavigateUp,
                title = {
                    Text(
                        text = stringResource(id = R.string.your_activity),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                showBackArrow = false,
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(spaceMedium)
            ){
                items(activities){activity ->
                    activity?.let {
                        ActivityItem(
                            activity = Activity(
                                activity.userId,
                                activityType = activity.activityType,
                                formattedTime = activity.formattedTime,
                                parentId = activity.parentId,
                                username = activity.username
                            ),
                            onNavigate = onNavigate
                        )
                    }
                }
            }
        }
        if(state.isLoading){
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}