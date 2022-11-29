package com.kuluruvineeth.socialnetwork.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.kuluruvineeth.socialnetwork.R
import com.kuluruvineeth.socialnetwork.core.domain.models.User
import com.kuluruvineeth.socialnetwork.presentation.components.StandardToolbar
import com.kuluruvineeth.socialnetwork.presentation.components.UserProfileItem
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.IconSizeMedium
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.spaceLarge
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.spaceMedium

@Composable
fun PersonListScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolbar(
            title = {
                Text(
                    text = stringResource(id = R.string.liked_by),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            navController = navController,
            showBackArrow = true
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(spaceLarge)
        ){
            items(10){
                UserProfileItem(
                    user = User(
                        profilePictureUrl = "",
                        username = "Kuluru Vineeth",
                        description = "Agririze is my Dream raa anthe....daaani saadinchaali",
                        followerCount = 234,
                        followingCount = 534,
                        postCount = 65
                    ),
                    actionIcon = {
                        Icon(
                            imageVector = Icons.Default.PersonAdd,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onBackground,
                            modifier = Modifier.size(IconSizeMedium)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(spaceMedium))
            }
        }
    }
}