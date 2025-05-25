package com.compose.cocoapod_sample.data.api.model

data class Character(
    val createdAt: String = "",
    val films: List<String> = emptyList(),
    val id: Int = 0,
    val imageUrl: String = "",
    val name: String = "",
    val parkAttractions: List<String> = emptyList(),
    val sourceUrl: String = "",
    val tvShows: List<String> = emptyList(),
    val updatedAt: String = "",
    val url: String = "",
    val v: Int = 0,
    val videoGames: List<String> = emptyList()
)
