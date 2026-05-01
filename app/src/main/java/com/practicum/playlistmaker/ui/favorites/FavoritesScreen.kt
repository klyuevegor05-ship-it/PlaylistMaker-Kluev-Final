package com.practicum.playlistmaker.ui.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.presentation.AppTrack
import com.practicum.playlistmaker.ui.components.MusicTrackItem
import com.practicum.playlistmaker.ui.materialTheme.YS
import androidx.compose.ui.graphics.Color
import com.practicum.playlistmaker.presentation.toAppTrack


@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    onBackClick: () -> Unit,
    onTrackClick: (AppTrack) -> Unit,
    isDarkTheme: Boolean
) {
    val favoriteList by viewModel.favoriteList.collectAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }

    val backgroundColor = if (isDarkTheme) Color(0xFF1A1B22) else Color.White
    val textColor = if (isDarkTheme) Color.White else Color(0xFF1A1B22)
    Color(0xFFAEAFB4)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(start = 16.dp)
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(R.drawable.ic_navigate_l),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = textColor
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = stringResource(R.string.favorites),
                    fontFamily = YS,
                    fontSize = 22.sp,
                    lineHeight = 26.sp,
                    fontWeight = FontWeight.Medium,
                    color = textColor
                )
            }

            if (favoriteList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(
                        modifier = Modifier.padding(top = 150.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        val emptyIcon =
                            if (isDarkTheme) R.drawable.ic_error_359_dark
                            else R.drawable.ic_error_359_light

                        Icon(
                            painter = painterResource(emptyIcon),
                            contentDescription = null,
                            modifier = Modifier.size(120.dp),
                            tint = Color.Unspecified
                        )

                        Text(
                            text = stringResource(R.string.favorites_empty),
                            fontFamily = YS,
                            fontSize = 19.sp,
                            lineHeight = 22.sp,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center,
                            color = textColor,
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(favoriteList) { _, track ->

                        MusicTrackItem(
                            trackInfo = track.toAppTrack(),
                            darkThemeEnabled = isDarkTheme,
                            onItemClick = { onTrackClick(track.toAppTrack()) },
                            onItemLongPress = {
                                viewModel.toggleFavorite(track, false)
                            }
                        )
                    }
                }
            }
        }
    }
}
