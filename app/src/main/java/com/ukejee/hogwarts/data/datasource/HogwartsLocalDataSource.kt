package com.ukejee.hogwarts.data.datasource

import com.ukejee.hogwarts.data.cache.model.PersistedHogwartsCharacter

interface HogwartsLocalDataSource {

    suspend fun saveCharacters(characters: List<PersistedHogwartsCharacter>)

    suspend fun saveCharacter(character: PersistedHogwartsCharacter)

    suspend fun getCharacters(): List<PersistedHogwartsCharacter>

    suspend fun removeAllCharacters()

    suspend fun removeCharacter(name: String)
}