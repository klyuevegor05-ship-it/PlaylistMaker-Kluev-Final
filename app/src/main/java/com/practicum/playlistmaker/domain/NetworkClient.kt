package com.practicum.playlistmaker.domain

import com.practicum.playlistmaker.data.dto.BaseResponse

interface NetworkClient {
    suspend fun doRequest(dto: Any): BaseResponse
}