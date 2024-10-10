package com.ukejee.hogwarts.data.adapter

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import com.ukejee.hogwarts.data.cache.model.PersistedHogwartsCharacter
import com.ukejee.hogwarts.data.network.model.HogwartsCharacterResponse
import com.ukejee.hogwarts.ui.characters.model.UiCharacter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object CharacterAdapter {

    private const val NOT_AVAILABLE = "N/A"

    fun toPersistedHogwartsCharacter(characterResponse: HogwartsCharacterResponse) =
        PersistedHogwartsCharacter(
            name = characterResponse.name,
            actor = if (characterResponse.actor.isNullOrEmpty()) NOT_AVAILABLE else characterResponse.actor,
            species = characterResponse.species,
            hogwartsStaff = characterResponse.hogwartsStaff,
            hogwartsStudent = characterResponse.hogwartsStudent,
            house = characterResponse.house,
            alive = characterResponse.alive,
            image = characterResponse.image,
            dateOfBirth = if (characterResponse.dateOfBirth.isNullOrEmpty()) NOT_AVAILABLE else characterResponse.dateOfBirth
        )

    fun toUiCharacter(persistedCharacter: PersistedHogwartsCharacter) =
        UiCharacter(
            name = persistedCharacter.name,
            actor = persistedCharacter.actor,
            species = persistedCharacter.species.capitalize(Locale.current),
            houseColor = getHouseColor(
                persistedCharacter.hogwartsStaff,
                persistedCharacter.hogwartsStudent,
                persistedCharacter.house
            ),
            alive = persistedCharacter.alive,
            image = persistedCharacter.image,
            dateOfBirth = changeDateFormat(persistedCharacter.dateOfBirth)
        )


    private fun getHouseColor(
        isHogwartsStaff: Boolean,
        isHogwartsStudent: Boolean,
        house: String
    ): String {
        return if (isHogwartsStudent || isHogwartsStaff) {
            House.entries.find { it.houseName == house }?.color ?: House.UNASSIGNED.color
        } else {
            House.UNASSIGNED.color
        }
    }

    private fun changeDateFormat(date: String): String {
        try {
            val localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            return localDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
        } catch (ex: Exception) {
            return NOT_AVAILABLE
        }
    }

}

enum class House(val houseName: String, val color: String) {
    GRYFFINDOR("Gryffindor", "#740001"),
    SLYTHERIN("Slytherin", "#1a472a"),
    RAVENCLAW("Ravenclaw", "#0c1a40"),
    HUFFLEPUFF("Hufflepuff", "#eeb939"),
    UNASSIGNED("Unassigned", "#000000")
}