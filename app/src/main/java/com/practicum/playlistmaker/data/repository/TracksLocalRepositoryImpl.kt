package com.practicum.playlistmaker.data.repository

import com.practicum.playlistmaker.data.dao.PlaylistDataAccessObject
import com.practicum.playlistmaker.data.dao.TrackDataAccessObject
import com.practicum.playlistmaker.data.entity.PlaylistTrackCrossRef
import com.practicum.playlistmaker.data.entity.TrackEntity
import com.practicum.playlistmaker.domain.Track
import com.practicum.playlistmaker.domain.TracksLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TracksLocalRepositoryImpl(
    private val trackDao: TrackDataAccessObject,
    private val playlistDao: PlaylistDataAccessObject
) : TracksLocalRepository {
    override suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) = withContext(Dispatchers.IO) {
        var entity = trackDao.getTrackById(track.trackId).firstOrNull()
        if (entity == null) {
            entity = TrackEntity(
                trackId = track.trackId,
                trackName = track.trackName,
                artistName = track.artistName,
                trackTimeMillis = track.trackTimeMillis,
                artworkUrl100 = track.artworkUrl100,
                previewUrl = track.previewUrl,
                isFavorite = track.favorite
            )
            trackDao.saveTrack(entity)
        }
        playlistDao.addCrossReference(PlaylistTrackCrossRef(playlistId, track.trackId))
    }

    override suspend fun deleteTrackFromPlaylist(trackId: Long, playlistId: Long) = withContext(Dispatchers.IO) {
        playlistDao.removeSpecificCrossRef(playlistId, trackId)
        val count = trackDao.getPlaylistCountForTrack(trackId)
        val entity = trackDao.getTrackById(trackId).firstOrNull()
        if (entity != null && !entity.isFavorite && count == 0L) {
            trackDao.removeTrack(entity)
        }
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) = withContext(Dispatchers.IO) {
        var existing = trackDao.getTrackById(track.trackId).firstOrNull()
        if (existing == null) {
            if (isFavorite) {
                existing = TrackEntity(
                    trackId = track.trackId,
                    trackName = track.trackName,
                    artistName = track.artistName,
                    trackTimeMillis = track.trackTimeMillis,
                    artworkUrl100 = track.artworkUrl100,
                    previewUrl = track.previewUrl,
                    isFavorite = true
                )
                trackDao.saveTrack(existing)
            }
        } else {
            val updated = existing.copy(isFavorite = isFavorite)
            trackDao.modifyTrack(updated)
            if (!isFavorite) {
                val count = trackDao.getPlaylistCountForTrack(track.trackId)
                if (count == 0L) {
                    trackDao.modifyTrack(updated)
                }
            }
        }
    }

    override fun getFavoriteTracks(): Flow<List<Track>> = trackDao.fetchFavoriteTracks().map { entities ->
        entities.map {
            Track(
                trackId = it.trackId,
                trackName = it.trackName,
                artistName = it.artistName,
                trackTimeMillis = it.trackTimeMillis,
                artworkUrl100 = it.artworkUrl100,
                previewUrl = it.previewUrl,
                favorite = it.isFavorite
            )
        }
    }

    override fun getTrackById(trackId: Long): Flow<Track?> = trackDao.getTrackById(trackId).map { entity ->
        entity?.let {
            Track(
                trackId = it.trackId,
                trackName = it.trackName,
                artistName = it.artistName,
                trackTimeMillis = it.trackTimeMillis,
                artworkUrl100 = it.artworkUrl100,
                previewUrl = it.previewUrl,
                favorite = it.isFavorite
            )
        }
    }
}