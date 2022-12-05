package com.kuluruvineeth.socialnetwork.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.ImageLoader
import com.kuluruvineeth.socialnetwork.R
import com.kuluruvineeth.socialnetwork.core.domain.models.User
import com.kuluruvineeth.socialnetwork.presentation.components.StandardToolbar
import com.kuluruvineeth.socialnetwork.presentation.components.UserProfileItem
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.IconSizeMedium
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.spaceLarge
import com.kuluruvineeth.socialnetwork.core.presentation.ui.theme.spaceMedium
import com.kuluruvineeth.socialnetwork.core.presentation.util.UiEvent
import com.kuluruvineeth.socialnetwork.core.presentation.util.asString
import com.kuluruvineeth.socialnetwork.core.util.Screen
import com.kuluruvineeth.socialnetwork.feature_post.presentation.person_list.PersonListEvent
import com.kuluruvineeth.socialnetwork.feature_post.presentation.person_list.PersonListViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PersonListScreen(
    scaffoldState: ScaffoldState,
    imageLoader: ImageLoader,
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: PersonListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val context = LocalContext.current
    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest{event ->
            when(event){
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        event.uiText.asString(context)
                    )
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
                    text = stringResource(id = R.string.liked_by),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            onNavigateUp = onNavigateUp,
            showBackArrow = true
        )
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(spaceLarge)
            ) {
                items(state.users) { user ->
                    UserProfileItem(
                        user = user,
                        imageLoader = imageLoader,
                        actionIcon = {
                            Icon(
                                imageVector = if (user.isFollowing) {
                                    Icons.Default.PersonRemove
                                } else Icons.Default.PersonAdd,
                                contentDescription = null,
                                tint = MaterialTheme.colors.onBackground,
                                modifier = Modifier.size(IconSizeMedium)
                            )
                        },
                        onItemClick = {
                            onNavigate(Screen.ProfileScreen.route + "?userId=${user.userId}")
                        },
                        onActionItemClick = {
                            viewModel.onEvent(PersonListEvent.ToggleFollowStateForUser(user.userId))
                        },
                        ownUserId = viewModel.ownUserId.value
                    )
                    Spacer(modifier = Modifier.height(spaceMedium))
                }
            }
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}