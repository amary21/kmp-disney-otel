package com.compose.cocoapod_sample.data.implementation.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CharacterResponse(
    @SerialName("createdAt")
    val createdAt: String? = null,
    @SerialName("films")
    val films: List<String?>? = null,
    @SerialName("_id")
    val id: Int? = null,
    @SerialName("imageUrl")
    val imageUrl: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("parkAttractions")
    val parkAttractions: List<String?>? = null,
    @SerialName("sourceUrl")
    val sourceUrl: String? = null,
    @SerialName("tvShows")
    val tvShows: List<String?>? = null,
    @SerialName("updatedAt")
    val updatedAt: String? = null,
    @SerialName("url")
    val url: String? = null,
    @SerialName("__v")
    val v: Int? = null,
    @SerialName("videoGames")
    val videoGames: List<String?>? = null
)