package com.compose.cocoapod_sample

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.compose.cocoapod_sample.data.di.disneyModule
import com.compose.cocoapod_sample.feature.list.ListRoute
import com.compose.cocoapod_sample.feature.list.ListViewModel
import com.compose.cocoapod_sample.feature.list.listScreen
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