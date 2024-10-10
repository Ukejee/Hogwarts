package com.ukejee.hogwarts.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ukejee.hogwarts.data.cache.model.PersistedHogwartsCharacter

@Dao
interface CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<PersistedHogwartsCharacter>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: PersistedHogwartsCharacter)

    @Query("Select * From characters Order By id")
    suspend fun getAllHogwartsCharacters(): List<PersistedHogwartsCharacter>

    @Query("Delete From characters")
    suspend fun deleteAllCharacters()

    @Query("Delete From characters WHERE name = :name")
    suspend fun deleteCharacters(name: String)

}