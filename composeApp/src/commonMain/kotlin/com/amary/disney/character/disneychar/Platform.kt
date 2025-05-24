package com.amary.disney.character.disneychar

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform