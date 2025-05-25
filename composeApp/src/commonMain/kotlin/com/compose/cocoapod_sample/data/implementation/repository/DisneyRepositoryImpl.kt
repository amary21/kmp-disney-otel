package com.compose.cocoapod_sample.data.implementation.repository

import com.compose.cocoapod_sample.data.api.model.Character
import com.compose.cocoapod_sample.data.api.repository.DisneyRepository
import com.compose.cocoapod_sample.data.implementation.mapper.toCharacter
import com.compose.cocoapod_sample.data.implementation.remote.api.DisneyApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

internal class DisneyRepositoryImpl(
    private val disneyApi: DisneyApi,
    private val ioDispatcher: CoroutineDispatcher,
): DisneyRepository {
    override suspend fun getCharacters(): List<Character> {
        return withContext(ioDispatcher) {
            disneyApi.getCharacters().data?.map { it.toCharacter() } ?: emptyList()
        }
    }
}