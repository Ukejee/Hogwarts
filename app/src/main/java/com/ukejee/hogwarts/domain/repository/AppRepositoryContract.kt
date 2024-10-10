package com.ukejee.hogwarts.domain.repository

import com.ukejee.hogwarts.data.DataSource
import com.ukejee.hogwarts.data.cache.model.PersistedHogwartsCharacter
import kotlinx.coroutines.flow.Flow

interface AppRepositoryContract {

    suspend fun getHogwartsCharacters(): Flow<DataSource<List<PersistedHogwartsCharacter>>>

    suspend fun refreshHogwartsCharacters()
}