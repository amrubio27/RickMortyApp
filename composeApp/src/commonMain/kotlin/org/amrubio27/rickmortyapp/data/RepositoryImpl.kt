package org.amrubio27.rickmortyapp.data

import org.amrubio27.rickmortyapp.data.remote.ApiService
import org.amrubio27.rickmortyapp.domain.Repository
import org.amrubio27.rickmortyapp.domain.model.CharacterModel

class RepositoryImpl(private val api: ApiService) : Repository {
    override suspend fun getSingleCharacter(id: String): CharacterModel {
        return api.getSingleCharacter(id).toDomain()

    }
}