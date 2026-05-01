package com.practicum.playlistmaker.domain

import kotlinx.coroutines.flow.Flow

interface PlaylistsRepository {
    fun getAllPlaylists(): Flow<List<Playlist>>
    fun getPlaylist(playlistId: Long): Flow<Playlist?>
    suspend fun addNewPlaylist(name: String, description: String, coverImageUri: String? = null)
    suspend fun deletePlaylistById(id: Long)
}