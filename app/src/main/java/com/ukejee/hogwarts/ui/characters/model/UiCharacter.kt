package com.ukejee.hogwarts.ui.characters.model

data class UiCharacter(
    val name: String,
    val actor: String,
    val species: String,
    val houseColor: String,
    val alive: Boolean,
    val image: String,
    val dateOfBirth: String
)
