package com.ukejee.hogwarts.ui.characters.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ukejee.hogwarts.data.adapter.House
import com.ukejee.hogwarts.ui.characters.model.UiCharacter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CharacterDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCharacterDetailsScreen() {

        val character = UiCharacter(
            name = "Harry Potter",
            actor = "Daniel Radcliff",
            species = "Human",
            alive = true,
            image = "https://ik.imagekit.io/hpapi/harry.jpg",
            houseColor = House.GRYFFINDOR.color,
            dateOfBirth = "7 Jul 1989"
        )

        composeTestRule.setContent {
            Details(
                characterName = character.name,
                actorName = character.actor,
                specie = character.species,
                isAlive = character.alive,
                imageUrl = character.image,
                dateOfBirth = character.dateOfBirth,
                houseColor = character.houseColor
            )
        }

        //Check all text correct details are displayed
        composeTestRule.onNodeWithText(character.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(character.actor).assertIsDisplayed()
        composeTestRule.onNodeWithText(character.species).assertIsDisplayed()
        composeTestRule.onNodeWithText("Alive").assertIsDisplayed()
        composeTestRule.onNodeWithText("Dead").assertDoesNotExist()
        composeTestRule.onNodeWithText(character.dateOfBirth).assertIsDisplayed()

    }
}