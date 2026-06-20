package org.amrubio27.rickmortyapp.ui.detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.amrubio27.rickmortyapp.domain.model.CharacterModel

class CharacterDetailViewModel(characterModel: CharacterModel) : ViewModel() {
    private val _uiState =
        MutableStateFlow<CharacterDetailState>(CharacterDetailState(characterModel))
    val uiState: StateFlow<CharacterDetailState> = _uiState
}