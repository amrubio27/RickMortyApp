package org.amrubio27.rickmortyapp.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import org.amrubio27.rickmortyapp.data.database.entity.CharacterOfTheDayEntity

@Dao
interface UserPreferenceDAO {
    @Query("SELECT * FROM characteroftheday")
    suspend fun getCharacterOfTheDayDB(): CharacterOfTheDayEntity?
}