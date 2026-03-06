package org.amrubio27.rickmortyapp.ui.home.tabs.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.amrubio27.rickmortyapp.domain.GetRandomCharacter
import org.amrubio27.rickmortyapp.domain.Repository
import org.amrubio27.rickmortyapp.domain.model.CharacterModel

class CharactersViewModel(
    val getRandomCharacter: GetRandomCharacter,
    private val repository: Repository
) : ViewModel() {
    private val _state = MutableStateFlow<CharactersState>(CharactersState())
    val state: StateFlow<CharactersState> = _state

    init {
        viewModelScope.launch(
            Dispatchers.IO
        ) {
            val result: CharacterModel = withContext(Dispatchers.IO) {
                getRandomCharacter()
            }
            _state.update { it.copy(characterOfTheDay = result) }
        }

        getAllCharacters()
    }

    fun getAllCharacters() {
        _state.update { it.copy(characters = repository.getAllCharacters()) }
    }
}