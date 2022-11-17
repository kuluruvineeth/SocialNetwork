package com.kuluruvineeth.socialnetwork.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuluruvineeth.socialnetwork.presentation.ui.theme.HintGray
import com.kuluruvineeth.socialnetwork.presentation.ui.theme.spaceMedium
import com.kuluruvineeth.socialnetwork.presentation.ui.theme.spaceSmall

@Composable
@Throws(IllegalArgumentException::class)
fun RowScope.StandardBottomNavItem(
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    icon: ImageVector,
    selected: Boolean = false,
    alertCount: Int? = null,
    selectedColor: Color = MaterialTheme.colors.primary,
    unselectedColor: Color = HintGray,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    if(alertCount != null && alertCount < 0){
        throw IllegalArgumentException("Alert count can't be negative")
    }
    BottomNavigationItem(
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        selectedContentColor = selectedColor,
        unselectedContentColor = unselectedColor,
        icon = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(spaceSmall)
                    .drawBehind {
                        if(selected){
                            drawLine(
                                color = if (selected) {
                                    selectedColor
                                } else {
                                    unselectedColor
                                },
                                start = Offset(size.width/2f - 15.dp.toPx(), size.height),
                                end = Offset(size.width/2f + 15.dp.toPx(), size.height),
                                strokeWidth = 2.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                        }
                    }
            ){
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                    modifier = Modifier.align(Alignment.Center)
                )
                if(alertCount != null){
                    val alertText = if(alertCount > 99){
                        "99+"
                    }else{
                        alertCount.toString()
                    }
                    Text(
                        text = alertText,
                        color = MaterialTheme.colors.onPrimary,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 10.sp,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .offset(10.dp)
                            .size(15.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colors.primary)
                    )
                }
            }
        }
    )
}