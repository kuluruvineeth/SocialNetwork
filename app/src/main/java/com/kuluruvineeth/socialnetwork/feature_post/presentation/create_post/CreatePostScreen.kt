package com.kuluruvineeth.socialnetwork.feature_post.presentation.create_post

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.kuluruvineeth.socialnetwork.presentation.components.StandardToolbar
import com.kuluruvineeth.socialnetwork.R
import com.kuluruvineeth.socialnetwork.presentation.components.StandardTextField
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.spaceLarge
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.spaceMedium
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.spaceSmall
import com.kuluruvineeth.socialnetwork.core.domain.states.StandardTextFieldState
import com.kuluruvineeth.socialnetwork.core.presentation.util.CropActivityResultContract
import com.kuluruvineeth.socialnetwork.core.presentation.util.UiEvent
import com.kuluruvineeth.socialnetwork.core.presentation.util.asString
import com.kuluruvineeth.socialnetwork.feature_post.presentation.util.PostConstants
import com.kuluruvineeth.socialnetwork.feature_post.presentation.util.PostDescriptionError
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun CreatePostScreen(
    imageLoader: ImageLoader,
    onNavigateUp: () -> Unit = {},
    onNavigate: (String) -> Unit = {},
    scaffoldState: ScaffoldState,
    viewModel: CreatePostViewModel = hiltViewModel()
) {
    val imageUri = viewModel.chosenImageUri.value
    
    val cropActivityLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(16f,9f))
    {
        viewModel.onEvent(CreatePostEvent.CropImage(it))
    }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ){
        cropActivityLauncher.launch(it)
    }
    val context = LocalContext.current

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest{event ->
            when(event){
                is UiEvent.ShowSnackbar -> {
                    GlobalScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = event.uiText.asString(context)
                        )
                    }
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
                        text = stringResource(id = R.string.create_a_new_post),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onBackground
                    )
            }, 
            onNavigateUp = onNavigateUp,
            showBackArrow = true
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(spaceLarge)
        ) {
            Box(
                modifier = Modifier
                    .aspectRatio(16f / 9f)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.onBackground,
                        shape = MaterialTheme.shapes.medium
                    )
                    .clickable {
                        galleryLauncher.launch("image/*")
                    },
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = Icons.Default.Add, 
                    contentDescription = stringResource(id = R.string.choose_image),
                    tint = MaterialTheme.colors.onBackground
                )
                imageUri?.let { uri ->
                    Image(
                        painter = rememberImagePainter(
                            data = uri,
                            imageLoader = imageLoader
                        ),
                        contentDescription = stringResource(id = R.string.post_image),
                        modifier = Modifier.matchParentSize()
                    )
                }
            }
            Spacer(modifier = Modifier.height(spaceMedium))
            StandardTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                text = viewModel.descriptionState.value.text,
                hint = stringResource(id = R.string.description),
                error = when(viewModel.descriptionState.value.error){
                    is PostDescriptionError.FieldEmpty -> stringResource(
                        id = R.string.this_field_cant_be_empty
                    )
                    else -> ""
                                                                    },
                singleLine = false,
                maxLines = 5,
                maxLength = PostConstants.MAX_POST_DESCRIPTION_LENGTH,
                onValueChange = {
                    viewModel.onEvent(
                        CreatePostEvent.EnterDescription(it)
                    )
                }
            )
            Spacer(modifier = Modifier.height(spaceLarge))
            Button(
                onClick = {
                          viewModel.onEvent(
                              CreatePostEvent.PostImage
                          )
                },
                enabled = !viewModel.isLoading.value,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = stringResource(id = R.string.post),
                    color = MaterialTheme.colors.onPrimary
                )
                Spacer(modifier = Modifier.width(spaceSmall))
                if(viewModel.isLoading.value){
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                            .size(20.dp)
                            .align(CenterVertically)
                    )
                }else{
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null
                    )
                }
            }
        }
    }

}