package org.amrubio27.rickmortyapp.ui.detail

import org.amrubio27.rickmortyapp.domain.model.CharacterModel
import org.amrubio27.rickmortyapp.domain.model.EpisodeModel

data class CharacterDetailState(
    val characterModel: CharacterModel,
    val episodes: List<EpisodeModel>? = null
)
