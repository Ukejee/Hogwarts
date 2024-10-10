package com.ukejee.hogwarts.data.datasource

import com.ukejee.hogwarts.data.cache.AppDatabase
import com.ukejee.hogwarts.data.cache.model.PersistedHogwartsCharacter
import javax.inject.Inject

class HogwartsRoomDataSource @Inject constructor(
    appDatabase: AppDatabase
) : HogwartsLocalDataSource {

    private val characterDao = appDatabase.getCharactersDao()

    override suspend fun saveCharacters(characters: List<PersistedHogwartsCharacter>) {
        characterDao.insertAll(characters)
    }

    override suspend fun saveCharacter(character: PersistedHogwartsCharacter) {
        characterDao.insertCharacter(character)
    }

    override suspend fun getCharacters(): List<PersistedHogwartsCharacter> =
        characterDao.getAllHogwartsCharacters()


    override suspend fun removeAllCharacters() {
        characterDao.deleteAllCharacters()
    }

    override suspend fun removeCharacter(name: String) {
        characterDao.deleteCharacters(name)
    }

}