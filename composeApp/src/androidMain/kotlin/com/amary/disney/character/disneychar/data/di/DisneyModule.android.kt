package com.amary.disney.character.disneychar.data.di

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*

actual fun httpClientEngine(): HttpClientEngine {
    return OkHttp.create()
}