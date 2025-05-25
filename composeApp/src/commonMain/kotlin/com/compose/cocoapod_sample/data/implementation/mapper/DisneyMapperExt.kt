package com.compose.cocoapod_sample.data.implementation.mapper

import com.compose.cocoapod_sample.data.api.model.Character
import com.compose.cocoapod_sample.data.implementation.remote.response.CharacterResponse

internal fun CharacterResponse.toCharacter() = Character(
    createdAt = createdAt.orEmpty(),
    films = films.orEmpty().map { it.orEmpty() },
    id = id ?: 0,
    imageUrl = imageUrl.orEmpty(),
    name = name.orEmpty(),
    parkAttractions = parkAttractions.orEmpty().map { it.orEmpty() },
    sourceUrl = sourceUrl.orEmpty(),
    tvShows = tvShows.orEmpty().map { it.orEmpty() },
    updatedAt = updatedAt.orEmpty(),
    url = url.orEmpty(),
    v = v ?: 0,
    videoGames = videoGames.orEmpty().map { it.orEmpty() }
)