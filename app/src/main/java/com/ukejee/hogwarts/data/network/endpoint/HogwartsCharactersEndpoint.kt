package com.ukejee.hogwarts.data.network.endpoint

import com.ukejee.hogwarts.data.network.model.HogwartsCharacterResponse
import retrofit2.http.GET

interface HogwartsCharactersEndpoint {

    @GET("api/characters")
    suspend fun getHogwartsCharacters(): List<HogwartsCharacterResponse>
}