package com.compose.cocoapod_sample.data.implementation.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class InfoResponse(
    @SerialName("data")
    val data: List<CharacterResponse>? = null,
)