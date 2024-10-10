package com.ukejee.hogwarts.testHelpers

import com.google.gson.annotations.SerializedName
import com.ukejee.hogwarts.data.DataSource
import com.ukejee.hogwarts.data.adapter.House
import com.ukejee.hogwarts.data.cache.model.PersistedHogwartsCharacter
import com.ukejee.hogwarts.data.network.model.HogwartsCharacterResponse
import com.ukejee.hogwarts.data.network.model.Wand
import com.ukejee.hogwarts.domain.repository.AppRepositoryContract
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration.Companion.seconds

class FakeSuccessRepository : AppRepositoryContract {
    override suspend fun getHogwartsCharacters(): Flow<DataSource<List<PersistedHogwartsCharacter>>> {
        return flow {
            delay(2.seconds)
            emit(
                DataSource.success(
                    listOf(
                        createPersistedHogwartsCharacter(
                            name = "Harry Potter",
                            actor = "Daniel Radcliff",
                            dateOfBirth = "31-07-1989"
                        ),
                        createPersistedHogwartsCharacter(
                            name = "Ron Weasley",
                            actor = "Actor Name",
                            dateOfBirth = "31-07-1983"
                        ),
                        createPersistedHogwartsCharacter(
                            name = "Mary Jane",
                            actor = "Maria Janet",
                            species = "Human",
                            dateOfBirth = "29-05-1990"
                        ),
                        createPersistedHogwartsCharacter(
                            name = "Hermonie's Cat",
                            actor = "",
                            species = "Cat",
                            dateOfBirth = "31-04-1999"
                        ),
                        createPersistedHogwartsCharacter(
                            name = "Jack Potter",
                            actor = "Daniel Radcliff",
                            dateOfBirth = "31-07-1989"
                        )
                    )
                )
            )
        }
    }

    override suspend fun refreshHogwartsCharacters() {

    }

}

fun createPersistedHogwartsCharacter(
    name: String,
    actor: String,
    species: String = "Human",
    house: String = House.GRYFFINDOR.houseName,
    isHogwartsStaff: Boolean = false,
    isHogwartsStudent: Boolean = true,
    dateOfBirth: String = "31-07-1989"
) = PersistedHogwartsCharacter(
    name = name,
    actor = actor,
    species = species,
    hogwartsStaff = isHogwartsStaff,
    hogwartsStudent = isHogwartsStudent,
    house = house,
    alive = true,
    dateOfBirth = dateOfBirth,
    image = "testImage"
)

fun createHogwartsCharacterResponse(
    name: String,
    actor: String,
    species: String = "Human",
    isHogwartsStaff: Boolean = false,
    isHogwartsStudent: Boolean = true,
    dateOfBirth: String = "31-07-1989"
) = HogwartsCharacterResponse(
    name = name,
    actor = actor,
    alive = true,
    alternateNames = listOf("", ""),
    alternateActors = listOf(""),
    ancestry = "testAncrstry",
    dateOfBirth = dateOfBirth,
    eyeColour = "testEyeColour",
    gender = "testGender",
    hairColour = "testHairColour",
    hogwartsStaff = isHogwartsStaff,
    hogwartsStudent = isHogwartsStudent,
    species = species,
    house = House.GRYFFINDOR.houseName,
    id = "testId",
    image = "testImage",
    patronus = "testPatronus",
    wizard = true,
    yearOfBirth = 1989,
    wand = Wand(core = "testCore", length = 1.06, wood = "testWood")
)