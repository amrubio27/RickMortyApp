package org.amrubio27.rickmortyapp.domain

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.amrubio27.rickmortyapp.domain.model.CharacterModel
import org.amrubio27.rickmortyapp.domain.model.CharacterOfTheDayModel
import kotlin.time.Clock
import kotlin.time.Instant

class GetRandomCharacter(val repository: Repository) {
    suspend operator fun invoke(): CharacterModel {

        val characterOfTheDay: CharacterOfTheDayModel? = repository.getCharacterDB()
        val selectedDay = getCurrentDayOfTheYear()
        return if (characterOfTheDay != null && characterOfTheDay.selectedDay == selectedDay) {
            characterOfTheDay.characterModel
        } else {
            val result = generateRandomCharacter()
            repository.saveCharacterDB(
                CharacterOfTheDayModel(
                    characterModel = result,
                    selectedDay = selectedDay
                )
            )
            result
        }
    }

    private suspend fun generateRandomCharacter(): CharacterModel {
        val random: Int = (1..826).random()
        return repository.getSingleCharacter(random.toString())
    }

    private fun getCurrentDayOfTheYear(): String {
        val instant: Instant = Clock.System.now()
        val localTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return "${localTime.dayOfYear}${localTime.year}"
    }
}