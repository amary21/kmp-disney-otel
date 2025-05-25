package com.compose.cocoapod_sample.data.implementation.remote.api

import com.compose.cocoapod_sample.data.implementation.remote.response.InfoResponse

internal interface DisneyApi {
    suspend fun getCharacters(): InfoResponse
}