package com.amary.disney.character.disneychar

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.amary.disney.character.disneychar.data.di.disneyModule
import com.amary.disney.character.disneychar.feature.list.ListRoute
import com.amary.disney.character.disneychar.feature.list.ListViewModel
import com.amary.disney.character.disneychar.feature.list.listScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

@Composable
@Preview
fun App() {
    KoinApplication(
        application = {
            modules(
                disneyModule +
                module {
                    viewModel { ListViewModel(get()) }
                }
            )
        }
    ) {
        MaterialTheme {
            val navigator = rememberNavController()
            NavHost(
                navController = navigator,
                startDestination = ListRoute
            ) {
                listScreen()
            }
        }
    }
}