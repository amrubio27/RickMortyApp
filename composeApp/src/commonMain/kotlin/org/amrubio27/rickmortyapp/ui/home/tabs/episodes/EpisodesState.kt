package org.amrubio27.rickmortyapp.ui.home.tabs.episodes

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.amrubio27.rickmortyapp.domain.model.EpisodeModel

data class EpisodesState(val episodes: Flow<PagingData<EpisodeModel>> = emptyFlow())