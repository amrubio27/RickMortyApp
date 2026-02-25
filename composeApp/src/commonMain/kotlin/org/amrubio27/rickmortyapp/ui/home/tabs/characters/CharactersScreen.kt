package org.amrubio27.rickmortyapp.ui.home.tabs.characters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CharactersScreen() {
    val charactersViewModels = koinViewModel<CharactersViewModel>()
    val state by charactersViewModels.state.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
    ) {
        state.characterOfTheDay?.let {
            Text(it.image)
        }
    }
}