package org.amrubio27.rickmortyapp.ui.home.tabs.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.amrubio27.rickmortyapp.domain.Repository

class EpisodesViewModel(private val repository: Repository) : ViewModel() {

    private val _state = MutableStateFlow(EpisodesState())
    val state: StateFlow<EpisodesState> = _state.asStateFlow()

    init {
        _state.update {
            it.copy(
                episodes = repository.getAllEpisodes().cachedIn(viewModelScope)
            )
        }
    }
}