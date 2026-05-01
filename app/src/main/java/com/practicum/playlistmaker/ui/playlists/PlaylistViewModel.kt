package com.practicum.playlistmaker.ui.playlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.domain.Playlist
import com.practicum.playlistmaker.domain.PlaylistsRepository
import com.practicum.playlistmaker.domain.Track
import com.practicum.playlistmaker.domain.TracksLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val playlistsRepository: PlaylistsRepository,
    private val tracksLocalRepository: TracksLocalRepository
) : ViewModel() {
    val playlists: Flow<List<Playlist>> = playlistsRepository.getAllPlaylists().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _coverImageUri = MutableStateFlow<String?>(null)
    val coverImageUri: Flow<String?> = _coverImageUri.asStateFlow()

    fun setCoverImageUri(uri: String?) {
        _coverImageUri.value = uri
    }

    fun getPlaylist(id: Long): Flow<Playlist?> = playlistsRepository.getPlaylist(id)

    fun createNewPlaylist(name: String, description: String) {
        viewModelScope.launch {
            playlistsRepository.addNewPlaylist(name, description, _coverImageUri.value)
        }
    }

    fun deletePlaylistById(id: Long) {
        viewModelScope.launch {
            playlistsRepository.deletePlaylistById(id)
        }
    }

    fun addTrackToPlaylist(track: Track, playlistId: Long) {
        viewModelScope.launch {
            tracksLocalRepository.insertTrackToPlaylist(track, playlistId)
        }
    }

    fun toggleFavorite(track: Track, isFavorite: Boolean) {
        viewModelScope.launch {
            tracksLocalRepository.updateTrackFavoriteStatus(track, isFavorite)
        }
    }

    fun getTrackById(trackId: Long): Flow<Track?> = tracksLocalRepository.getTrackById(trackId)

    fun deleteTrackFromPlaylist(trackId: Long, playlistId: Long) {
        viewModelScope.launch {
            tracksLocalRepository.deleteTrackFromPlaylist(trackId, playlistId)
        }
    }
}