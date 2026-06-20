package org.amrubio27.rickmortyapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.amrubio27.rickmortyapp.domain.model.CharacterModel
import org.amrubio27.rickmortyapp.domain.model.CharacterOfTheDayModel

@Entity(tableName = "characteroftheday")
data class CharacterOfTheDayEntity(
    @PrimaryKey val id: Int,
    val isAlive: Boolean,
    val image: String,
    val name: String,
    val selectedDay: String,
    val species: String
) {
    fun toDomain(): CharacterOfTheDayModel? {
        return CharacterOfTheDayModel(
            characterModel = CharacterModel(
                id = id,
                isAlive = isAlive,
                image = image,
                name = name,
                species = species
            ),
            selectedDay = selectedDay
        )
    }
}