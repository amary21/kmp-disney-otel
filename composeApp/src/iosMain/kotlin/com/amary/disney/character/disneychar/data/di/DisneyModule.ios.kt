package com.amary.disney.character.disneychar.data.di

import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*

actual fun httpClientEngine(): HttpClientEngine {
    return Darwin.create()
}