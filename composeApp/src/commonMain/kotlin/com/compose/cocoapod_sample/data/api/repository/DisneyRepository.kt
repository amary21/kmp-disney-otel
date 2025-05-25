package com.compose.cocoapod_sample.data.api.repository

import com.compose.cocoapod_sample.data.api.model.Character

interface DisneyRepository {
    suspend fun getCharacters(): List<Character>
}
