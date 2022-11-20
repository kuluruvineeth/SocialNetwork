package com.kuluruvineeth.socialnetwork.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kuluruvineeth.socialnetwork.R
import com.kuluruvineeth.socialnetwork.domain.models.User
import com.kuluruvineeth.socialnetwork.presentation.components.StandardTextField
import com.kuluruvineeth.socialnetwork.presentation.components.StandardToolbar
import com.kuluruvineeth.socialnetwork.presentation.components.UserProfileItem
import com.kuluruvineeth.socialnetwork.presentation.ui.theme.IconSizeMedium
import com.kuluruvineeth.socialnetwork.presentation.ui.theme.spaceLarge
import com.kuluruvineeth.socialnetwork.presentation.ui.theme.spaceMedium
import com.kuluruvineeth.socialnetwork.presentation.ui.theme.spaceSmall
import com.kuluruvineeth.socialnetwork.presentation.util.StandardTextFieldState

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