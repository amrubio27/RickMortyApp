package org.amrubio27.rickmortyapp.domain

import org.amrubio27.rickmortyapp.domain.model.CharacterModel

interface Repository {
    suspend fun getSingleCharacter(id: String): CharacterModel
}