package com.amary.disney.character.disneychar.data.implementation.remote.api

import com.amary.disney.character.disneychar.data.implementation.remote.response.CharacterResponse

internal interface DisneyApi {
    suspend fun getCharacters(): List<CharacterResponse>
}