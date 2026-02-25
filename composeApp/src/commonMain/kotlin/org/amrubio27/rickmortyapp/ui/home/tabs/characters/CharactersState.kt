package org.amrubio27.rickmortyapp.ui.home.tabs.characters

import org.amrubio27.rickmortyapp.domain.model.CharacterModel

data class CharactersState(
    val characterOfTheDay: CharacterModel? = null
)