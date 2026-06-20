package org.amrubio27.rickmortyapp.di

import org.amrubio27.rickmortyapp.ui.detail.CharacterDetailViewModel
import org.amrubio27.rickmortyapp.ui.home.tabs.characters.CharactersViewModel
import org.amrubio27.rickmortyapp.ui.home.tabs.episodes.EpisodesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::EpisodesViewModel)
    viewModelOf(::CharactersViewModel)
    viewModelOf(::CharacterDetailViewModel)
}