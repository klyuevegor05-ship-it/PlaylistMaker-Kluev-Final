package com.practicum.playlistmaker.ui.playlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practicum.playlistmaker.domain.PlaylistsRepository
import com.practicum.playlistmaker.domain.TracksLocalRepository

class PlaylistViewModelFactory(
    private val playlistsRepository: PlaylistsRepository,
    private val tracksLocalRepository: TracksLocalRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaylistViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlaylistViewModel(playlistsRepository, tracksLocalRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}