package org.amrubio27.rickmortyapp.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.amrubio27.rickmortyapp.domain.model.CharacterModel

@Serializable
data class CharacterResponse(
    @SerialName("id") val parameter: Int,
    val status: String,
    val image: String
) {
    fun toDomain(): CharacterModel {
        return CharacterModel(
            id = parameter,
            isAlive = status.lowercase() == "alive",
            image = image
        )
    }
}
