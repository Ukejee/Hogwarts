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
class CharacterListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCharacterListScreen() {
        val searchResults = listOf(
            UiCharacter(
                name = "Harry Potter",
                actor = "Daniel Radcliff",
                species = "Human",
                alive = true,
                image = "testImage",
                houseColor = House.GRYFFINDOR.color,
                dateOfBirth = "7 Jul 1989"
            ),
            UiCharacter(
                name = "John Doe",
                actor = "Jack Long",
                species = "Human",
                alive = false,
                image = "testImage",
                houseColor = House.GRYFFINDOR.color,
                dateOfBirth = "17 Jun 1989"
            ),
            UiCharacter(
                name = "Ron Weasley",
                actor = "Sam Jackman",
                species = "Human",
                alive = true,
                image = "testImage",
                houseColor = House.SLYTHERIN.color,
                dateOfBirth = "17 Jan 1986"
            ),
            UiCharacter(
                name = "Mary Jane",
                actor = "Maria Janet",
                species = "Human",
                alive = true,
                image = "testImage",
                houseColor = House.GRYFFINDOR.color,
                dateOfBirth = "21 Oct 1989"
            ),
            UiCharacter(
                name = "Harry Potter's Bird",
                actor = "",
                species = "Bird",
                alive = true,
                image = "testImage",
                houseColor = House.UNASSIGNED.color,
                dateOfBirth = "7 Jul 2000"
            ),
        )
        composeTestRule.setContent {
            CharacterListSearchScreen(
                searchQuery = "test",
                searchResults = searchResults,
                onSearchQueryChange = { },
                onClick = { }
            )
        }

        //Check if all items are displayed
        searchResults.forEach { item ->
            composeTestRule.onNodeWithText(item.name).assertIsDisplayed()
            composeTestRule.onNodeWithText(item.actor).assertIsDisplayed()
        }

        //Check search query is displayed
        composeTestRule.onNodeWithText("test").assertIsDisplayed()
    }
}