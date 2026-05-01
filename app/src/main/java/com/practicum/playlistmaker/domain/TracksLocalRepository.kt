package com.practicum.playlistmaker.domain

import kotlinx.coroutines.flow.Flow

interface TracksLocalRepository {
    suspend fun insertTrackToPlaylist(track: Track, playlistId: Long)
    suspend fun deleteTrackFromPlaylist(trackId: Long, playlistId: Long)
    suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean)
    fun getFavoriteTracks(): Flow<List<Track>>
    fun getTrackById(trackId: Long): Flow<Track?>
}