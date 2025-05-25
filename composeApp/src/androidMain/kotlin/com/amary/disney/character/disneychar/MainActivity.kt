package com.amary.disney.character.disneychar

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.amary.disney.character.disneychar.data.api.otel.OpenTelemetryService

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        Log.e("onCreate", "HELLO FROM ANDROID")
        setContent {
            App(OpenTelemetryServiceImpl())
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(object: OpenTelemetryService{
        override fun build(httpEndpoint: String, authorization: String, serviceName: String, hostName: String) {}
        override fun trace(instrumentationName: String, instrumentationVersion: String) {}
    })
}