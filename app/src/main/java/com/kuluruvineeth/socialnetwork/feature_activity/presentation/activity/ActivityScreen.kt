package com.kuluruvineeth.socialnetwork.feature_activity.presentation.activity

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kuluruvineeth.socialnetwork.R
import com.kuluruvineeth.socialnetwork.core.domain.models.Activity
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.spaceMedium
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.spaceSmall
import com.kuluruvineeth.socialnetwork.feature_activity.domain.ActivityAction
import com.kuluruvineeth.socialnetwork.core.util.DateFormatUtil
import com.kuluruvineeth.socialnetwork.presentation.components.StandardToolbar
import kotlin.random.Random

@Composable
fun ActivityScreen(
    navController: NavController,
    viewModel: ActivityViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        StandardToolbar(navController = navController, title = {
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
            items(20){
                ActivityItem(
                    activity = Activity(
                        username = "Kuluru Vineeth",
                        actionType = if(Random.nextInt(2)==0){
                            ActivityAction.LikedPost
                        }else ActivityAction.CommentedOnPost,
                        formattedTime = DateFormatUtil.timestampToFormattedString(
                            timestamp = System.currentTimeMillis(),
                            pattern = "MMM dd, HH:mm"
                        )
                    ),
                    modifier = Modifier
                        .padding(spaceSmall)
                )
                if(it < 19){
                    Spacer(modifier = Modifier.height(spaceMedium))
                }
            }
        }
    }
}