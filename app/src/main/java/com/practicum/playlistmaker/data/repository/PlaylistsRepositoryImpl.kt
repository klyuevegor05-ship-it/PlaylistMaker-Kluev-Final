package com.practicum.playlistmaker.data.repository

import com.practicum.playlistmaker.data.dao.PlaylistDataAccessObject
import com.practicum.playlistmaker.data.dao.TrackDataAccessObject
import com.practicum.playlistmaker.data.entity.PlaylistEntity
import com.practicum.playlistmaker.domain.Playlist
import com.practicum.playlistmaker.domain.PlaylistsRepository
import com.practicum.playlistmaker.domain.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PlaylistsRepositoryImpl(
    private val playlistDao: PlaylistDataAccessObject,
    private val trackDao: TrackDataAccessObject
) : PlaylistsRepository {
    override fun getAllPlaylists(): Flow<List<Playlist>> = playlistDao.fetchAllPlaylists().map { playlistsWithTracks ->
        playlistsWithTracks.map { pwt ->
            Playlist(
                id = pwt.playlist.id,
                name = pwt.playlist.name,
                description = pwt.playlist.description,
                tracks = pwt.tracks.map { te ->
                    Track(
                        trackId = te.trackId,
                        trackName = te.trackName,
                        artistName = te.artistName,
                        trackTimeMillis = te.trackTimeMillis,
                        artworkUrl100 = te.artworkUrl100,
                        previewUrl = te.previewUrl,
                        favorite = te.isFavorite
                    )
                },
                coverImageUri = pwt.playlist.coverImageUri
            )
        }
    }

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> = playlistDao.fetchPlaylistById(playlistId).map { pwt ->
        pwt?.let {
            Playlist(
                id = it.playlist.id,
                name = it.playlist.name,
                description = it.playlist.description,
                tracks = it.tracks.map { te ->
                    Track(
                        trackId = te.trackId,
                        trackName = te.trackName,
                        artistName = te.artistName,
                        trackTimeMillis = te.trackTimeMillis,
                        artworkUrl100 = te.artworkUrl100,
                        previewUrl = te.previewUrl,
                        favorite = te.isFavorite
                    )
                },
                coverImageUri = it.playlist.coverImageUri
            )
        }
    }

    override suspend fun addNewPlaylist(name: String, description: String, coverImageUri: String?) = withContext(Dispatchers.IO) {
        playlistDao.addPlaylist(PlaylistEntity(name = name, description = description, coverImageUri = coverImageUri))
    }

    override suspend fun deletePlaylistById(id: Long) = withContext(Dispatchers.IO) {
        playlistDao.clearCrossRefsForPlaylist(id)
        playlistDao.removePlaylist(PlaylistEntity(id = id, name = "", description = ""))
    }
}