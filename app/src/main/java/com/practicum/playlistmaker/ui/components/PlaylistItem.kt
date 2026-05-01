package com.practicum.playlistmaker.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.domain.Playlist
import com.practicum.playlistmaker.ui.materialTheme.YS
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.core.net.toUri

@Composable
fun PlaylistItem(
    playlistData: Playlist,
    textColor: Color,
    secondaryColor: Color,
    chevronColor: Color? = null,
    bgColor: Color,
    onItemClick: () -> Unit,
    onItemLongPress: () -> Unit = {},
    displayChevron: Boolean = true
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(61.dp)
            .background(bgColor)
            .combinedClickable(
                onClick = onItemClick,
                onLongClick = onItemLongPress
            )
            .padding(horizontal = 13.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        playlistData.coverImageUri?.let { uriString ->
            AsyncImage(
                model = uriString.toUri(),
                contentDescription = null,
                modifier = Modifier.size(45.dp),
                contentScale = ContentScale.Crop
            )
        } ?: Image(
            painter = painterResource(R.drawable.ic_input_photo),
            contentDescription = null,
            colorFilter = ColorFilter.tint(secondaryColor),
            modifier = Modifier.size(45.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = playlistData.name,
                fontFamily = YS,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 19.sp,
                color = textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = pluralStringResource(R.plurals.tracks, playlistData.tracks.size, playlistData.tracks.size),
                fontFamily = YS,
                fontWeight = FontWeight.Normal,
                fontSize = 11.sp,
                lineHeight = 13.sp,
                color = secondaryColor
            )
        }
        if (displayChevron) {
            Icon(
                painter = painterResource(R.drawable.ic_stripe_right),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = chevronColor ?: secondaryColor
            )
        }
    }
}