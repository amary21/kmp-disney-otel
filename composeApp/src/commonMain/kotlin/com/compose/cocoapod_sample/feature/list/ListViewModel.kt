package com.compose.cocoapod_sample.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.cocoapod_sample.data.api.model.Character
import com.compose.cocoapod_sample.data.api.repository.DisneyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListViewModel(
    private val disneyRepository: DisneyRepository
) : ViewModel() {
    private val _charState = MutableStateFlow<List<Character>>(emptyList())
    val charState get() = _charState.asStateFlow()

    fun getList() {
        viewModelScope.launch {
            try {
                val characters = disneyRepository.getCharacters()
                _charState.update { characters }
            } catch (e: Exception) {
                e.printStackTrace()
                _charState.update { emptyList() }
            }
        }
    }
}