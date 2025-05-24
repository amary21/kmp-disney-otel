package com.amary.disney.character.disneychar.data.implementation.repository

import com.amary.disney.character.disneychar.data.api.model.Character
import com.amary.disney.character.disneychar.data.api.repository.DisneyRepository
import com.amary.disney.character.disneychar.data.implementation.mapper.toCharacter
import com.amary.disney.character.disneychar.data.implementation.remote.api.DisneyApi
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