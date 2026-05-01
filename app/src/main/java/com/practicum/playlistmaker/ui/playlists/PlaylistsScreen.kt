package com.practicum.playlistmaker.ui.playlists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.ui.materialTheme.YS
import com.practicum.playlistmaker.ui.components.PlaylistItem

@Composable
fun PlaylistsScreen(
    viewModel: PlaylistViewModel,
    onCreatePlaylist: () -> Unit,
    onOpenPlaylist: (Long) -> Unit,
    onBackClick: () -> Unit,
    isDarkTheme: Boolean
) {
    val playlists by viewModel.playlists.collectAsState(emptyList())
    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedPlaylistId by remember { mutableStateOf<Long?>(null) }
    var selectedPlaylistName by remember { mutableStateOf("") }

    val backgroundColor = if (isDarkTheme) Color(0xFF1A1B22) else Color.White
    val textColor = if (isDarkTheme) Color.White else Color(0xFF1A1B22)
    val borderColor = Color(0xFFAEAFB4)
    val fabBg = if (isDarkTheme) Color.White.copy(alpha = 0.25f) else Color(0xFF1A1B22).copy(alpha = 0.25f)

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
                    text = stringResource(R.string.playlists),
                    fontFamily = YS,
                    fontSize = 22.sp,
                    lineHeight = 26.sp,
                    fontWeight = FontWeight.Medium,
                    color = textColor
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 0.dp)
            ) {
                items(playlists, key = { it.id }) { pl ->
                    PlaylistItem(
                        playlistData = pl,
                        textColor = textColor,
                        secondaryColor = borderColor,
                        chevronColor = borderColor,
                        bgColor = backgroundColor,
                        onItemClick = { onOpenPlaylist(pl.id) },
                        onItemLongPress = {
                            selectedPlaylistId = pl.id
                            selectedPlaylistName = pl.name
                            showDeleteDialog = true
                        },
                        displayChevron = true
                    )
                }
            }
        }
        FloatingActionButton(
            onClick = onCreatePlaylist,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 32.dp)
                .size(51.dp),
            containerColor = fabBg,
            contentColor = Color.White,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(0.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_input),
                contentDescription = stringResource(R.string.create_playlist),
                tint = Color.White,
                modifier = Modifier.padding(13.dp)
            )
        }
    }

    if (showDeleteDialog && selectedPlaylistId != null) {
        AlertDialog(
            shape = RoundedCornerShape(4.dp),
            onDismissRequest = { showDeleteDialog = false },
            containerColor = Color.White,
            tonalElevation = 16.dp,
            title = null,
            text = {
                Text(
                    text = stringResource(
                        R.string.delete_playlist_message,
                        selectedPlaylistName
                    ),
                    color = Color(0xFF1A1B22),
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    letterSpacing = 0.25.sp,
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deletePlaylistById(selectedPlaylistId!!)
                    showDeleteDialog = false
                }) {
                    Text(
                        text = stringResource(R.string.confirm).uppercase(),
                        color = Color(0xFF3772E7),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 1.25.sp
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text(
                        text = stringResource(R.string.cancel).uppercase(),
                        color = Color(0xFF3772E7),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 1.25.sp
                    )
                }
            }
        )
    }
}