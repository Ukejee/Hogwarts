package com.ukejee.hogwarts.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ukejee.hogwarts.data.cache.dao.CharactersDao
import com.ukejee.hogwarts.data.cache.model.PersistedHogwartsCharacter

@Database(
    entities = [PersistedHogwartsCharacter::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getCharactersDao(): CharactersDao
}