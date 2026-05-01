package com.practicum.playlistmaker.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmaker.domain.Track
import com.practicum.playlistmaker.domain.TracksLocalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val tracksLocalRepository: TracksLocalRepository
) : ViewModel() {
    private val _favoriteList = MutableStateFlow<List<Track>>(emptyList())
    val favoriteList = _favoriteList.asStateFlow()

    fun loadFavorites() {
        viewModelScope.launch {
            tracksLocalRepository.getFavoriteTracks().collectLatest { favorites ->
                _favoriteList.value = favorites
            }
        }
    }

    fun toggleFavorite(track: Track, isFavorite: Boolean) {
        viewModelScope.launch {
            tracksLocalRepository.updateTrackFavoriteStatus(track, isFavorite)
        }
    }
}