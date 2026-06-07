package org.amrubio27.rickmortyapp.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.amrubio27.rickmortyapp.domain.model.CharacterModel
import org.amrubio27.rickmortyapp.domain.model.CharacterOfTheDayModel
import org.amrubio27.rickmortyapp.domain.model.EpisodeModel

interface Repository {
    suspend fun getSingleCharacter(id: String): CharacterModel
    fun getAllCharacters(): Flow<PagingData<CharacterModel>>
    fun getAllEpisodes(): Flow<PagingData<EpisodeModel>>
    suspend fun getCharacterDB(): CharacterOfTheDayModel?
    suspend fun saveCharacterDB(characterOfTheDayModel: CharacterOfTheDayModel)

}