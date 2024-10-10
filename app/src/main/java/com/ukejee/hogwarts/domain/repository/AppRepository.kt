package com.ukejee.hogwarts.domain.repository

import android.util.Log
import com.ukejee.hogwarts.data.DataSource
import com.ukejee.hogwarts.data.adapter.CharacterAdapter
import com.ukejee.hogwarts.data.cache.model.PersistedHogwartsCharacter
import com.ukejee.hogwarts.data.datasource.HogwartsLocalDataSource
import com.ukejee.hogwarts.data.datasource.HogwartsRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val hogwartsLocalDataSource: HogwartsLocalDataSource,
    private val hogwartsRemoteDataSource: HogwartsRemoteDataSource
) : AppRepositoryContract {

    //var status: DataSource<Nothing> = TODO()

    override suspend fun getHogwartsCharacters(): Flow<DataSource<List<PersistedHogwartsCharacter>>> {
        return flow {
            try {
                val characters = hogwartsLocalDataSource.getCharacters()
                Log.d("isis", "$characters")
                if (characters.isEmpty()) {
                    emit(DataSource.empty())
                } else {
                    emit(DataSource.success(characters))
                }
            } catch (ex: Exception) {
                emit(DataSource.error(ex.message ?: "Something went wrong...", null))
            }
        }
    }

    override suspend fun refreshHogwartsCharacters() {
        withContext(Dispatchers.IO) {
            try {
                val result = hogwartsRemoteDataSource.fetchHogwartsCharacters()
                if (result.isNotEmpty()) {
                    hogwartsLocalDataSource.removeAllCharacters()
                    hogwartsLocalDataSource.saveCharacters(result.map { CharacterAdapter.toPersistedHogwartsCharacter(it) })
                } else {

                }
            } catch (ex: Exception) {
                Log.d("isis", "$ex")
//                status = if(ex is IOException) {
//                    DataSource.error("No Internet Connection", null)
//                } else {
//                    DataSource.error("Something went wrong...", null)
//                }
            }
        }
    }
}