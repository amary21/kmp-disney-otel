package com.amary.disney.character.disneychar.data.di

import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*
import platform.Foundation.NSProcessInfo

actual fun httpClientEngine(): HttpClientEngine {
    return Darwin.create()
}

actual fun getHostName(): String {
    return NSProcessInfo.processInfo.hostName
}