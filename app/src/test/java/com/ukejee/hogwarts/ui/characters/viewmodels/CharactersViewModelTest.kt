package com.ukejee.hogwarts.ui.characters.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ukejee.hogwarts.data.DataSource
import com.ukejee.hogwarts.data.Status
import com.ukejee.hogwarts.data.adapter.CharacterAdapter
import com.ukejee.hogwarts.testHelpers.FakeSuccessRepository
import com.ukejee.hogwarts.testHelpers.MainCoroutineScopeRule
import com.ukejee.hogwarts.testHelpers.createPersistedHogwartsCharacter
import com.ukejee.hogwarts.ui.characters.model.UiCharacter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharactersViewModelTest {

    private val fakeSuccessRepository = FakeSuccessRepository()

    private lateinit var viewModel: CharactersViewModel

    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()

    @Before
    fun setUp() {
        viewModel = CharactersViewModel(fakeSuccessRepository)
    }

    @Test
    fun `get characters should first emit a loading status`() = runTest {
        val list: MutableList<DataSource<List<UiCharacter>>> = mutableListOf()

        viewModel.characters.first {
            list.add(it)
        }

        val expected = DataSource.loading(null)
        assertEquals(expected.status, list.first().status)
    }

    @Test
    fun `get characters should emit a correct DataSource when the app repository returns a successful response`() = runTest {
        val list = viewModel.characters.take(2).toList()

        val expected = Status.SUCCESS
        assertEquals(expected, list.last().status)
    }

    @Test
    fun `searchResult returns the right list of characters given a searchQuery`() = runTest {
        viewModel.onSearchQueryChanged("Potter")
        val list = viewModel.searchResults.take(2).toList()

        val excepted = CharacterAdapter.toUiCharacter(
            createPersistedHogwartsCharacter(
                name = "Harry Potter",
                actor = "Daniel Radcliff",
                dateOfBirth = "31-07-1989"
            )
        )

        assertEquals(2, list.last().data?.size)
        assertEquals(excepted, list.last().data?.first())
    }

}