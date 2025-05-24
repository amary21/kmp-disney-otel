package com.amary.disney.character.disneychar.data.implementation.remote.api

import com.amary.disney.character.disneychar.data.implementation.remote.response.CharacterResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher

internal class DisneyApiImpl(
    private val httpClient: HttpClient,
    private val ioDispatcher: CoroutineDispatcher,
): DisneyApi {
    override suspend fun getCharacters(): List<CharacterResponse> {
       return with(ioDispatcher) {
           httpClient.get("/character").body()
       }
    }
}