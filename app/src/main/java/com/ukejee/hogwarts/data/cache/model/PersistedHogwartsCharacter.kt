package com.ukejee.hogwarts.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class PersistedHogwartsCharacter(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val actor: String,
    val species: String,
    val hogwartsStaff: Boolean,
    val hogwartsStudent: Boolean,
    val house: String,
    val alive: Boolean,
    val image: String,
    val dateOfBirth: String
)
