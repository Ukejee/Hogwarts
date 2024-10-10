package com.ukejee.hogwarts.ui.characters.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ukejee.hogwarts.ui.characters.viewmodels.CharactersViewModel
import com.ukejee.hogwarts.ui.theme.HogwartsTheme

@Composable
fun CharacterDetailsScreen(viewModel: CharactersViewModel) {

    val character = viewModel.selectedCharacter.collectAsState().value

    character?.let {
        Details(
            characterName = character.name,
            actorName = character.actor,
            specie = character.species,
            dateOfBirth = character.dateOfBirth,
            isAlive = character.alive,
            imageUrl = character.image,
            houseColor = character.houseColor
        )
    }
}

@Composable
fun Details(
    paddingValues: PaddingValues = PaddingValues(8.dp),
    characterName: String,
    actorName: String,
    specie: String,
    dateOfBirth: String,
    isAlive: Boolean,
    imageUrl: String?,
    houseColor: String
) {
    Column(
        modifier = Modifier
            .background(color = Color(android.graphics.Color.parseColor(houseColor)))
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .padding(8.dp),
            shape = RoundedCornerShape(0.dp),
        ){
            Column(
                modifier = Modifier.background(Color.White),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = imageUrl,
                    contentDescription = null,
                )
            }
        }
        Text(
            text = characterName,
            fontSize = 28.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = "Played By: ",
                fontSize = 16.sp,
                color = Color.White,
            )
            Text(
                text = actorName,
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Start
            )
        }

        Row(
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = "Specie: ",
                fontSize = 16.sp,
                color = Color.White,
            )
            Text(
                text = specie,
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Start
            )
        }

        Row(
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = "Date of Birth: ",
                fontSize = 16.sp,
                color = Color.White,
            )
            Text(
                text = dateOfBirth,
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Start
            )
        }

        Row(
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = "Status: ",
                fontSize = 16.sp,
                color = Color.White,
            )
            Text(
                text = if (isAlive) "Alive" else "Dead",
                fontSize = 16.sp,
                color = Color.White,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Preview
@Composable
fun DetailsPreview() {
    HogwartsTheme {
        Details(
            characterName = "Harry Potter",
            actorName = "Daniel Radcliff",
            specie = "Human",
            dateOfBirth = "30 July 1985",
            isAlive = true,
            imageUrl = "Arid",
            houseColor = "#740001"
        )
    }
}