package com.kuluruvineeth.socialnetwork.feature_profile.presentation.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import com.kuluruvineeth.socialnetwork.R
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.spaceSmall
import com.kuluruvineeth.socialnetwork.core.util.toPx
import com.kuluruvineeth.socialnetwork.feature_profile.domain.model.Skill

@Composable
fun BannerSection(
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    iconSize: Dp = 35.dp,
    leftIconModifier: Modifier = Modifier,
    rightIconModifier: Modifier = Modifier,
    bannerUrl: String? = null,
    topSkills: List<Skill> = emptyList(),
    shouldShowGitHub: Boolean = false,
    shouldShowInstagram: Boolean = false,
    shouldShowLinkedIn: Boolean = false,
    onGithubClick: () -> Unit = {},
    onKotlinClick: () -> Unit = {},
    onLinkedInClick: () -> Unit = {}
) {
    BoxWithConstraints(
        modifier = modifier
    ){
        Image(
            painter = rememberImagePainter(
                data = bannerUrl,
                imageLoader = imageLoader
            ),
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
            topSkills.forEach { skill ->
                Spacer(modifier = Modifier.width(spaceSmall))
                Image(
                    painter = rememberImagePainter(
                        data = skill.imageUrl,
                        imageLoader = imageLoader,
                    ),
                    contentDescription = null,
                    modifier = Modifier.height(iconSize)
                )
            }
        }

        Row(
            modifier = rightIconModifier
                .height(iconSize)
                .align(Alignment.BottomEnd)
                .padding(spaceSmall)
        ) {
            if(shouldShowGitHub){
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
            }
            if(shouldShowInstagram){
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
            }
            if(shouldShowInstagram){
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
}
