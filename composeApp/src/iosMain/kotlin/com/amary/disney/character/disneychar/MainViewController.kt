package com.amary.disney.character.disneychar

import androidx.compose.ui.window.ComposeUIViewController
import com.amary.disney.character.disneychar.data.api.otel.OpenTelemetryService

fun MainViewController(
    openTelemetryService: OpenTelemetryService
) = ComposeUIViewController { App(openTelemetryService) }