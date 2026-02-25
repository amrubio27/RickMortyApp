package org.amrubio27.rickmortyapp.domain

import org.amrubio27.rickmortyapp.domain.model.CharacterModel

class GetRandomCharacter(val repository: Repository) {
    suspend operator fun invoke(): CharacterModel {
        val random = (1..826).random()
        return repository.getSingleCharacter(random.toString())
    }
}