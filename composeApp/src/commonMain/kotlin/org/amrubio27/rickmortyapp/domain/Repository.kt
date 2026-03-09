package org.amrubio27.rickmortyapp.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.amrubio27.rickmortyapp.domain.model.CharacterModel

interface Repository {
    suspend fun getSingleCharacter(id: String): CharacterModel
    fun getAllCharacters(): Flow<PagingData<CharacterModel>>
}