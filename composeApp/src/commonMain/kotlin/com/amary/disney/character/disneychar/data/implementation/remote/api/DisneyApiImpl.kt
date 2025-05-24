package com.amary.disney.character.disneychar.data.implementation.remote.api

import com.amary.disney.character.disneychar.data.implementation.remote.response.CharacterResponse
import com.amary.disney.character.disneychar.data.implementation.remote.response.InfoResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher

internal class DisneyApiImpl(
    private val httpClient: HttpClient,
    private val ioDispatcher: CoroutineDispatcher,
): DisneyApi {
    override suspend fun getCharacters(): InfoResponse {
       return with(ioDispatcher) {
           httpClient.get("/character").body()
       }
    }
}