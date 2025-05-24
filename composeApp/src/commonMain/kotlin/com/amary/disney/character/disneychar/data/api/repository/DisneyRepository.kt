package com.amary.disney.character.disneychar.data.api.repository

import com.amary.disney.character.disneychar.data.api.model.Character

interface DisneyRepository {
    suspend fun getCharacters(): List<Character>
}
