package com.ukejee.hogwarts.data.datasource

import com.ukejee.hogwarts.data.network.model.HogwartsCharacterResponse

interface HogwartsRemoteDataSource {

    suspend fun fetchHogwartsCharacters(): List<HogwartsCharacterResponse>
}