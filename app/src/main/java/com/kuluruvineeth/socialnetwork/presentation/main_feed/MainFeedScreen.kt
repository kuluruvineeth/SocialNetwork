package com.kuluruvineeth.socialnetwork.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kuluruvineeth.socialnetwork.presentation.components.Post
import com.kuluruvineeth.socialnetwork.presentation.components.StandardScaffold

@Composable
fun MainFeedScreen(
    navController: NavController
) {
    Post(
        post = com.kuluruvineeth.socialnetwork.domain.models.Post(
            username = "Kuluru Vineeth",
            imageUrl = "",
            profilePictureUrl = "",
            description = "Agririze(Close to my heart) is the passion that i am " +
                    "living with for sure will make it go live by the end of 2023",
            likeCount = 17,
            commentCount = 10
        )
    )
}