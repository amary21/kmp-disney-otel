package com.amary.disney.character.disneychar.feature.list

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
@SerialName("list")
data object ListRoute

fun NavGraphBuilder.listScreen() {
    composable<ListRoute> {
        val viewModel: ListViewModel = koinViewModel()
        val characters by viewModel.charState.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            viewModel.getList()
        }

        ListScreen(characters = characters)
    }
}

internal fun NavHostController.navigateToList() {
    navigate(ListRoute)
}