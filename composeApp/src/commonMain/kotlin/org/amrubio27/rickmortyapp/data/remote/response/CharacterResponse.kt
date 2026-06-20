package org.amrubio27.rickmortyapp.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.amrubio27.rickmortyapp.domain.model.CharacterModel

@Serializable
data class CharacterResponse(
    @SerialName("id") val parameter: Int,
    @SerialName("status") val status: String,
    @SerialName("image") val image: String,
    @SerialName("name") val name: String,
    @SerialName("species") val species: String
) {
    fun toDomain(): CharacterModel {
        return CharacterModel(
            id = parameter,
            isAlive = status.lowercase() == "alive",
            image = image,
            name = name,
            species = species
        )
    }
}
