package com.kuluruvineeth.socialnetwork.presentation.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kuluruvineeth.socialnetwork.R
import com.kuluruvineeth.socialnetwork.presentation.ui.theme.ProfilePictureSizeLarge
import com.kuluruvineeth.socialnetwork.presentation.ui.theme.spaceLarge
import com.kuluruvineeth.socialnetwork.presentation.ui.theme.spaceSmall
import com.kuluruvineeth.socialnetwork.presentation.util.toPx

@Composable
fun BannerSection(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    iconSize: Dp = 35.dp,
    leftIconModifier: Modifier = Modifier,
    rightIconModifier: Modifier = Modifier,
    onGithubClick: () -> Unit = {},
    onKotlinClick: () -> Unit = {},
    onLinkedInClick: () -> Unit = {}
) {
    BoxWithConstraints(
        modifier = modifier
    ){
        Image(
            painter = painterResource(id = R.drawable.channelart),
            contentDescription = stringResource(id = R.string.banner_image),
            contentScale = ContentScale.Crop,
            modifier = imageModifier
                .fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = constraints.maxHeight - iconSize.toPx() * 2f
                    )
                )
        )
        Row(
            modifier = leftIconModifier
                .height(iconSize)
                .align(Alignment.BottomStart)
                .padding(spaceSmall)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_github_icon_1),
                contentDescription = "Github",
                modifier = Modifier.size(iconSize)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_kotlin_logo),
                contentDescription = "Kotlin",
                modifier = Modifier.size(iconSize)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_github_icon_1),
                contentDescription = "Github",
                modifier = Modifier.size(iconSize)
            )
        }

        Row(
            modifier = rightIconModifier
                .height(iconSize)
                .align(Alignment.BottomEnd)
                .padding(spaceSmall)
        ) {
            IconButton(
                onClick = onGithubClick,
                modifier = Modifier
                    .size(iconSize)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_github_icon_1),
                    contentDescription = "Github",
                    modifier = Modifier.size(iconSize)
                )
            }
            IconButton(
                onClick = onLinkedInClick,
                modifier = Modifier
                    .size(iconSize)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_linkedin_icon_1),
                    contentDescription = "LinkedIn",
                    modifier = Modifier.size(iconSize)
                )
            }
            IconButton(
                onClick = onKotlinClick,
                modifier = Modifier
                    .size(iconSize)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_kotlin_logo),
                    contentDescription = "Kotlin",
                    modifier = Modifier.size(iconSize)
                )
            }
        }
    }
}
