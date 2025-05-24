package com.amary.disney.character.disneychar.data.implementation.remote.api

import com.amary.disney.character.disneychar.data.implementation.remote.response.CharacterResponse
import com.amary.disney.character.disneychar.data.implementation.remote.response.InfoResponse

internal interface DisneyApi {
    suspend fun getCharacters(): InfoResponse
}