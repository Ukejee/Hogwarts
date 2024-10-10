package com.ukejee.hogwarts.data.adapter

import com.ukejee.hogwarts.data.cache.model.PersistedHogwartsCharacter
import com.ukejee.hogwarts.testHelpers.createHogwartsCharacterResponse
import com.ukejee.hogwarts.testHelpers.createPersistedHogwartsCharacter
import com.ukejee.hogwarts.ui.characters.model.UiCharacter
import org.junit.Assert.*
import org.junit.Test

class CharacterAdapterTest {

    @Test
    fun `calling toPersistedHogwartsCharacter on a given HogwartsCharacterResponse returns right PersistedHogwartsCharacter`() {
        val hogwartsCharacterResponse = createHogwartsCharacterResponse(
            "Harry Potter",
            "Daniel Radcliff",
            dateOfBirth = "31-09-1890"
            )

        val expected = PersistedHogwartsCharacter(
            name = "Harry Potter",
            actor = "Daniel Radcliff",
            species = "Human",
            hogwartsStaff = false,
            hogwartsStudent = true,
            house = House.GRYFFINDOR.houseName,
            alive = true,
            dateOfBirth = "31-09-1890",
            image = "testImage"
        )

        assertEquals(expected, CharacterAdapter.toPersistedHogwartsCharacter(hogwartsCharacterResponse))
    }

    @Test
    fun `calling toUiCharacter on a given PersistedHogwartsCharacter returns correct UiCharacter`() {

        val persistedHogwartsCharacter = createPersistedHogwartsCharacter(
            name = "John Doe",
            actor = "Jack Long",
            dateOfBirth = "23-04-1995",
            house = House.RAVENCLAW.houseName
        )

        val expected = UiCharacter(
            name = "John Doe",
            actor = "Jack Long",
            species = "Human",
            houseColor = House.RAVENCLAW.color,
            alive = true,
            image = "testImage",
            dateOfBirth = "23 Apr 1995"

        )

        assertEquals(expected, CharacterAdapter.toUiCharacter(persistedHogwartsCharacter))
    }
}