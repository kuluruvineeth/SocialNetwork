package com.kuluruvineeth.socialnetwork.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.kuluruvineeth.socialnetwork.R
import com.kuluruvineeth.socialnetwork.domain.models.Activity
import com.kuluruvineeth.socialnetwork.domain.util.ActivityAction
import com.kuluruvineeth.socialnetwork.domain.util.DateFormatUtil
import com.kuluruvineeth.socialnetwork.presentation.activity.ActivityItem
import com.kuluruvineeth.socialnetwork.presentation.components.StandardScaffold
import com.kuluruvineeth.socialnetwork.presentation.components.StandardToolbar
import com.kuluruvineeth.socialnetwork.presentation.ui.theme.spaceMedium
import com.kuluruvineeth.socialnetwork.presentation.ui.theme.spaceSmall
import kotlin.random.Random

@Composable
fun ProfileScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        StandardToolbar(navController = navController, title = {
            Text(
                text = stringResource(id = R.string.your_profile),
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
            item{

            }
            items(20){

            }
        }
    }
}