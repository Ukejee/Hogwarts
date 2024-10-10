package com.ukejee.hogwarts.ui.characters.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ukejee.hogwarts.data.DataSource
import com.ukejee.hogwarts.data.Status
import com.ukejee.hogwarts.data.adapter.CharacterAdapter
import com.ukejee.hogwarts.domain.repository.AppRepositoryContract
import com.ukejee.hogwarts.ui.characters.model.UiCharacter
import com.ukejee.hogwarts.ui.navigation.AppBarState
import com.ukejee.hogwarts.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val appRepository: AppRepositoryContract
) : ViewModel()  {

    private val _characters = MutableStateFlow<DataSource<List<UiCharacter>>>(DataSource.loading(null))
    val characters get() = _characters

    private val _appBarState = MutableStateFlow(AppBarState("Movies", false))
    val appBarState get() = _appBarState

    var searchQuery by mutableStateOf("")
        private set

    private val _selectedCharacter = MutableStateFlow<UiCharacter?>(null)
    val selectedCharacter get() = _selectedCharacter

    val searchResults: StateFlow<DataSource<List<UiCharacter>>> =
        snapshotFlow { searchQuery }
            .combine(characters) {searchQuery, characters ->
                when {
                    searchQuery.isNotEmpty() && characters.status == Status.SUCCESS ->
                        DataSource.success(characters.data?.filter {character ->
                            character.name.contains(searchQuery, ignoreCase = true) ||
                                    character.actor.contains(searchQuery, ignoreCase = true)
                        })
                    else -> characters
                }
            }.stateIn(
                scope = viewModelScope,
                initialValue = DataSource.loading(null),
                started = SharingStarted.WhileSubscribed(5_000)
            )

    fun onSearchQueryChanged(newQuery: String) {
        searchQuery = newQuery
    }

    fun onCharacterSelected(character: UiCharacter) {
        viewModelScope.launch {
            _selectedCharacter.emit(character)
            _appBarState.emit(AppBarState(Screen.Details.label, true))
        }
    }

    fun onBackButtonPressed() {
        viewModelScope.launch {
            _appBarState.emit(AppBarState(Screen.Home.label, false))
        }
    }

    init {
        viewModelScope.launch {
            appRepository.refreshHogwartsCharacters()
            appRepository.getHogwartsCharacters().collect { characters ->
                when (characters.status) {
                    Status.EMPTY -> {
                        _characters.emit(DataSource.empty())
                    }

                    Status.SUCCESS -> {
                        _characters.emit(DataSource.success(characters.data?.map { CharacterAdapter.toUiCharacter(it) }))
                    }

                    else -> {
                        _characters.emit(DataSource.error(characters.message.toString(), null))
                    }
                }

            }
        }
    }
}