package com.kuluruvineeth.socialnetwork.domain.models

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val icon: ImageVector? = null,
    val contentDescription: String? = null,
    val alertCount: Int? = null,
    val route: String
)
