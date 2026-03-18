package org.amrubio27.rickmortyapp.domain

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.amrubio27.rickmortyapp.domain.model.CharacterModel
import kotlin.time.Clock
import kotlin.time.Instant

class GetRandomCharacter(val repository: Repository) {
    suspend operator fun invoke(): CharacterModel {

        //val characterDataBase = repository.getSavedCharacter()
        /*if (getCurrentDayOfTheYear() == characterDataBase.date){
            //pedirObjeto de base de datos
        }else{

        }*/
        //repository.getCharacterDB()

        val random = (1..826).random()
        return repository.getSingleCharacter(random.toString())
    }

    private fun getCurrentDayOfTheYear(): String {
        val instant: Instant = Clock.System.now()
        val localTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return "${localTime.dayOfYear}${localTime.year}"
    }
}