package com.kuluruvineeth.socialnetwork.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kuluruvineeth.socialnetwork.presentation.components.Post
import com.kuluruvineeth.socialnetwork.presentation.components.StandardScaffold
import com.kuluruvineeth.socialnetwork.R
import com.kuluruvineeth.socialnetwork.presentation.components.StandardToolbar
import com.kuluruvineeth.socialnetwork.presentation.util.Screen


@Composable
fun MainFeedScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        StandardToolbar(navController = navController, title = {
            Text(
                text = stringResource(id = R.string.your_feed),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground
            )
        }, modifier = Modifier.fillMaxWidth(), showBackArrow = true, navActions = {
            IconButton(onClick = {
                navController.navigate(Screen.SearchScreen.route)
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "",
                    tint = MaterialTheme.colors.onBackground
                )
            }
        })
        Post(
            post = com.kuluruvineeth.socialnetwork.domain.models.Post(
                username = "Kuluru Vineeth",
                imageUrl = "",
                profilePictureUrl = "",
                description = "Agririze(Close to my heart) is the passion that i am " + "living with for sure will make it go live by the end of 2023",
                likeCount = 17,
                commentCount = 10
            ),
            onPostClick = {
                navController.navigate(Screen.PostDetailScreen.route)
            }
        )
    }

}