package com.ukejee.hogwarts.data.datasource

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ukejee.hogwarts.data.adapter.House
import com.ukejee.hogwarts.data.cache.AppDatabase
import com.ukejee.hogwarts.data.cache.dao.CharactersDao
import com.ukejee.hogwarts.data.cache.model.PersistedHogwartsCharacter
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class HogwartsRoomDataSourceTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var charactersDao: CharactersDao
    private lateinit var db: AppDatabase


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        charactersDao = db.getCharactersDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun fromMovieRoomDataSource_inputOneEntry_returnsSameEntry() = runBlocking {

        val character = PersistedHogwartsCharacter(
            id = 1,
            name = "Harry Potter",
            actor = "Daniel Radcliff",
            species = "Human",
            hogwartsStaff = false,
            hogwartsStudent = true,
            house = House.GRYFFINDOR.houseName,
            alive = true,
            dateOfBirth = "7-08-1989",
            image = "testImage"
        )

        val sut = HogwartsRoomDataSource(db)
        sut.saveCharacter(character)
        val dbMovies = sut.getCharacters()

        assertEquals(1, dbMovies.size)
        assertEquals(character.name, dbMovies.first().name)
        assertEquals(character, dbMovies.first())

    }
}