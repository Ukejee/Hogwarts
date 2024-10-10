package com.ukejee.hogwarts.data.datasource

import com.ukejee.hogwarts.data.network.endpoint.HogwartsCharactersEndpoint
import com.ukejee.hogwarts.data.network.model.HogwartsCharacterResponse
import javax.inject.Inject

class HogwartsRetrofitDataSource @Inject constructor(
    private val hogwartsCharactersEndpoint: HogwartsCharactersEndpoint
): HogwartsRemoteDataSource {

    override suspend fun fetchHogwartsCharacters(): List<HogwartsCharacterResponse> {
        return hogwartsCharactersEndpoint.getHogwartsCharacters()
    }
}