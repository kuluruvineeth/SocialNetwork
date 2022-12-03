package com.kuluruvineeth.socialnetwork.feature_profile.presentation.edit_profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment
import com.kuluruvineeth.socialnetwork.presentation.components.StandardToolbar
import com.kuluruvineeth.socialnetwork.R
import com.kuluruvineeth.socialnetwork.presentation.components.StandardTextField
import com.kuluruvineeth.socialnetwork.feature_profile.presentation.edit_profile.components.Chip
import com.kuluruvineeth.socialnetwork.core.domain.states.StandardTextFieldState
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.ProfilePictureSizeLarge
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.spaceLarge
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.spaceMedium
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.spaceSmall
import com.kuluruvineeth.socialnetwork.core.presentation.util.CropActivityResultContract
import com.kuluruvineeth.socialnetwork.core.presentation.util.UiEvent
import com.kuluruvineeth.socialnetwork.core.presentation.util.asString
import com.kuluruvineeth.socialnetwork.feature_profile.presentation.util.EditProfileError
import kotlinx.coroutines.flow.collectLatest
import kotlin.random.Random


@Composable
fun EditProfileScreen(
    scaffoldState: ScaffoldState,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: EditProfileViewModel = hiltViewModel(),
    profilePictureSize: Dp = ProfilePictureSizeLarge
) {
    val profileState = viewModel.profileState.value
    
    val cropProfilePictureLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(1f,1f)
    ){
        viewModel.onEvent(EditProfileEvent.CropProfilePicture(it))
    }
    val cropBannerImageLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(5f,2f)
    ){
        viewModel.onEvent(EditProfileEvent.CropBannerImage(it))
    }
    val profilePictureGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){
        cropProfilePictureLauncher.launch(it)
    }
    val bannerImageGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){
        cropBannerImageLauncher.launch(it)
    }

    val context = LocalContext.current
    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest{event ->
            when(event){
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
                is UiEvent.NavigateUp -> {
                    onNavigateUp()
                }
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardToolbar(
            title = {
                    Text(
                        text = stringResource(id = R.string.edit_your_profile),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground
                    )
            },
            onNavigateUp = onNavigateUp,
            showBackArrow = true,
            navActions = {
                IconButton(onClick = {
                    viewModel.onEvent(EditProfileEvent.UpdateProfile)
                }) {
                    Icon(
                        imageVector = Icons.Default.Check, 
                        contentDescription = stringResource(id = R.string.save_changes),
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            BannerEditSection(
                bannerImage = rememberImagePainter(
                    data = viewModel.bannerUri.value ?: profileState.profile?.bannerUrl,
                    builder = {
                        crossfade(true)
                    }
                ),
                profileImage = rememberImagePainter(
                    data = viewModel.profilePictureUri.value ?: profileState.profile?.profilePictureUrl,
                    builder = {
                        crossfade(true)
                    }
                ),
                profilePictureSize = profilePictureSize,
                onBannerClick = {
                    bannerImageGalleryLauncher.launch("image/*")
                },
                onProfilePictureClick = {
                    profilePictureGalleryLauncher.launch("image/*")
                }
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(spaceLarge)
            ) {
                Spacer(modifier = Modifier.height(spaceMedium))
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.usernameState.value.text,
                    hint = stringResource(id = R.string.username),
                    error = when(viewModel.usernameState.value.error){
                        is EditProfileError.FieldEmpty -> stringResource(
                            id = R.string.this_field_cant_be_empty
                        )
                        else -> ""
                                                                     },
                    leadingIcon = Icons.Default.Person,
                    onValueChange = {
                        viewModel.onEvent(
                            EditProfileEvent.EnteredUsername(it)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(spaceMedium))
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.githubTextFieldState.value.text,
                    hint = stringResource(id = R.string.github_profile_url),
                    error = when(viewModel.githubTextFieldState.value.error){
                        is EditProfileError.FieldEmpty -> stringResource(
                            id = R.string.this_field_cant_be_empty
                        )
                        else -> ""
                    },
                    leadingIcon = ImageVector.vectorResource(id = R.drawable.ic_github_icon_1),
                    onValueChange = {
                        viewModel.onEvent(
                            EditProfileEvent.EnteredGithubUrl(it)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(spaceMedium))
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.instagramTextFieldState.value.text,
                    hint = stringResource(id = R.string.instagram_profile_url),
                    error = when(viewModel.instagramTextFieldState.value.error){
                        is EditProfileError.FieldEmpty -> stringResource(
                            id = R.string.this_field_cant_be_empty
                        )
                        else -> ""
                    },
                    leadingIcon = ImageVector.vectorResource(id = R.drawable.ic_kotlin_logo),
                    onValueChange = {
                        viewModel.onEvent(
                            EditProfileEvent.EnteredInstagramUrl(it)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(spaceMedium))
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.linkedInTextFieldState.value.text,
                    hint = stringResource(id = R.string.linked_in_profile_url),
                    error = when(viewModel.linkedInTextFieldState.value.error){
                        is EditProfileError.FieldEmpty -> stringResource(
                            id = R.string.this_field_cant_be_empty
                        )
                        else -> ""
                    },
                    leadingIcon = ImageVector.vectorResource(id = R.drawable.ic_linkedin_icon_1),
                    onValueChange = {
                        viewModel.onEvent(
                            EditProfileEvent.EnteredLinkedInUrl(it)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(spaceMedium))
                StandardTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = viewModel.bioState.value.text,
                    hint = stringResource(id = R.string.your_bio),
                    error = when(viewModel.bioState.value.error){
                        is EditProfileError.FieldEmpty -> stringResource(
                            id = R.string.this_field_cant_be_empty
                        )
                        else -> ""
                    },
                    singleLine = false,
                    maxLines = 3,
                    leadingIcon = Icons.Default.Description,
                    onValueChange = {
                        viewModel.onEvent(
                            EditProfileEvent.EnteredBio(it)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(spaceSmall))
                Text(
                    text = stringResource(id = R.string.select_top_3_skills),
                    style = MaterialTheme.typography.h2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(spaceLarge))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    mainAxisAlignment = MainAxisAlignment.Center,
                    mainAxisSpacing = spaceMedium,
                    crossAxisSpacing = spaceMedium
                ){
                    viewModel.skills.value.skills.forEach {skill ->
                        Chip(
                            text = skill.name,
                            selected = viewModel.skills.value.selectedSkills.any {
                                it.name == skill.name
                            },
                            onChipClick = {
                                viewModel.onEvent(EditProfileEvent.SetSkillsSelected(skill))
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun BannerEditSection(
    bannerImage: Painter,
    profileImage: Painter,
    profilePictureSize: Dp = ProfilePictureSizeLarge,
    onBannerClick: () -> Unit = {},
    onProfilePictureClick: () -> Unit = {}
) {
    val bannerHeight = (LocalConfiguration.current.screenWidthDp / 2.5f).dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(bannerHeight + profilePictureSize / 2f)
    ){
        Image(
            painter = bannerImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(bannerHeight)
                .clickable { onBannerClick() }
        )
        Image(
            painter = profileImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(BottomCenter)
                .size(profilePictureSize)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onSurface,
                    shape = CircleShape
                )
                .clickable { onProfilePictureClick() }
        )
    }
}