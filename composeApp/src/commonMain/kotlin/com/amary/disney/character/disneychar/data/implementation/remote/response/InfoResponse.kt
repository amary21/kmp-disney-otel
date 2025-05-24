package com.amary.disney.character.disneychar.data.implementation.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class InfoResponse(
    @SerialName("data")
    val data: List<CharacterResponse>? = null,
)