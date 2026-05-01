package com.practicum.playlistmaker.data.repository

import com.practicum.playlistmaker.data.preferences.SearchHistoryPreferences
import com.practicum.playlistmaker.domain.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow

class SearchHistoryRepositoryImpl(
    private val preferences: SearchHistoryPreferences
) : SearchHistoryRepository {
    override fun addSearchQuery(query: String) {
        preferences.addEntry(query)
    }

    override fun getSearchHistory(): Flow<List<String>> = preferences.getEntries()
}