package com.kuluruvineeth.socialnetwork.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.kuluruvineeth.socialnetwork.presentation.components.Post

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