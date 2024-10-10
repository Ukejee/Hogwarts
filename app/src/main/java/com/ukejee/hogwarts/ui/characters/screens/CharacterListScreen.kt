package com.ukejee.hogwarts.ui.characters.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ukejee.hogwarts.data.Status
import com.ukejee.hogwarts.ui.characters.viewmodels.CharactersViewModel
import com.ukejee.hogwarts.ui.theme.HogwartsTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ukejee.hogwarts.ui.characters.model.UiCharacter

@Composable
fun CharactersScreen(viewModel: CharactersViewModel, onClick: () -> Unit) {

    val characters = viewModel.characters.collectAsState().value
    val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
    when(characters.status) {
        Status.LOADING -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Status.EMPTY -> {
            Message("No Characters Found..")
        }
        Status.ERROR -> {
            characters.message?.let { Message(it) }
        }
        Status.SUCCESS -> {
            searchResults.data?.let {
                CharacterListSearchScreen(
                    searchQuery = viewModel.searchQuery,
                    searchResults = it,
                    onSearchQueryChange = viewModel::onSearchQueryChanged,
                    onClick = { item ->
                        viewModel.onCharacterSelected(item)
                        onClick()
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListSearchScreen(
    searchQuery: String,
    searchResults: List<UiCharacter>,
    onSearchQueryChange: (String) -> Unit,
    onClick: (UiCharacter) -> Unit
) {
    SearchBar(
        query = searchQuery,
        onQueryChange = onSearchQueryChange,
        onSearch = {},
        placeholder = {
            Text(text = "Search movies")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null
            )
        },
        trailingIcon = {},
        content = {
            LazyColumn {
                items(
                    count = searchResults.size,
                    itemContent = { index ->
                        val character = searchResults[index]
                        CharacterListItem(
                            character.name,
                            character.actor,
                            character.species,
                            character.houseColor,
                            onClick = { onClick(character) }
                        )
                    }
                )
            }
        },
        active = true,
        onActiveChange = {},
        tonalElevation = 0.dp
    )
}

@Composable
fun CharacterListItem(
    characterName: String,
    actorName: String,
    specie: String,
    houseColor: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick() }, onClickLabel = "See more details about $characterName")
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors().copy(containerColor =
            Color(android.graphics.Color.parseColor(houseColor)),
            contentColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = characterName,
                maxLines = 1,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Text(
                    text = "Played By: "
                )
                Text(
                    text = actorName,
                    maxLines = 2
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Text(
                    text = "Specie: "
                )
                Text(
                    text = specie,
                    maxLines = 2
                )
            }

        }

    }
}

@Composable
fun Message(message: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = message,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovieListItemPreview() {
    HogwartsTheme {
        CharacterListItem(
            characterName = "Harry Potter",
            actorName = "Daniel Radcliff",
            specie = "Human",
            houseColor = "#740001"
        ) { }
    }
}