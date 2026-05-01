package com.practicum.playlistmaker.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.ui.materialTheme.YS
private val primaryColor = Color(0xFF3772E7)
private val grayTextColor = Color(0xFFAEAFB4)
private val backgroundLight = Color.White
private val backgroundDark = Color(0xFF1A1B22)
private val titleDark = Color(0xFF1A1B22)
private val titleLight = Color.White

@Composable
fun MainScreen(
    onSearchSelected: () -> Unit,
    onSettingsSelected: () -> Unit,
    onPlaylistsSelected: () -> Unit,
    onFavoritesSelected: () -> Unit,
    darkThemeEnabled: Boolean
) {
    val chevronColor = if (darkThemeEnabled) titleLight else grayTextColor
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(primaryColor)
                .padding(horizontal = 16.dp, vertical = 28.dp)
        ) {
            Text(
                text = stringResource(R.string.playlist_maker),
                color = Color.White,
                fontFamily = YS,
                fontSize = 22.sp,
                lineHeight = 26.sp,
                fontWeight = FontWeight.Bold
            )
        }
        val bgColor = if (darkThemeEnabled) backgroundDark else backgroundLight
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(bgColor)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item { MenuItem(R.drawable.ic_seek_main, stringResource(R.string.search), onSearchSelected, chevronColor, darkThemeEnabled) }
                item { MenuItem(R.drawable.ic_playlists, stringResource(R.string.playlists), onPlaylistsSelected, chevronColor, darkThemeEnabled) }
                item { MenuItem(R.drawable.ic_like, stringResource(R.string.favorites), onFavoritesSelected, chevronColor, darkThemeEnabled) }
                item { MenuItem(R.drawable.ic_settings, stringResource(R.string.settings), onSettingsSelected, chevronColor, darkThemeEnabled) }
            }
        }
    }
}

@Composable
private fun MenuItem(
    iconResId: Int,
    title: String,
    onClickAction: () -> Unit,
    chevronColor: Color,
    darkTheme: Boolean
) {
    val itemColor = if (darkTheme) backgroundDark else backgroundLight
    val textColor = if (darkTheme) titleLight else titleDark
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp)
            .clickable { onClickAction() }
            .padding(horizontal = 16.dp)
            .background(itemColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = title,
            modifier = Modifier.size(24.dp),
            tint = textColor
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            color = textColor,
            fontFamily = YS,
            fontSize = 22.sp,
            lineHeight = 26.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_stripe_right),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = chevronColor
        )
    }
}