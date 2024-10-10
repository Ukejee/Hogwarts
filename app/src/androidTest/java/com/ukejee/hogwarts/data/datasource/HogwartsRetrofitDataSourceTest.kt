package com.ukejee.hogwarts.data.datasource

import androidx.test.platform.app.InstrumentationRegistry
import com.ukejee.hogwarts.application.HogwartsApplication
import com.ukejee.hogwarts.data.network.endpoint.HogwartsCharactersEndpoint
import com.ukejee.hogwarts.data.network.model.HogwartsCharacterResponse
import com.ukejee.hogwarts.data.network.model.Wand
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

class HogwartsRetrofitDataSourceTest {

    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(HogwartsCharactersEndpoint::class.java)

    private val sut = HogwartsRetrofitDataSource(api)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun fromGetPopularMovies_with200Response_returnsProperlyMappedObject() {
        mockWebServer.enqueueResponse("testGetHogwartsCharacterResponse.json", 200)

        runBlocking {
            val actual = sut.fetchHogwartsCharacters()

            val expectedResponse = listOf(
                HogwartsCharacterResponse(
                    id = "9e3f7ce4-b9a7-4244-b709-dae5c1f1d4a8",
                    name = "Harry Potter",
                    alternateNames = listOf("The Boy Who Lived", "The Chosen One", "Undesirable No. 1", "Potty"),
                    species = "human",
                    gender = "male",
                    house = "Gryffindor",
                    dateOfBirth = "31-07-1980",
                    yearOfBirth = 1980,
                    wizard = true,
                    ancestry = "half-blood",
                    eyeColour = "green",
                    hairColour = "black",
                    wand = Wand(core = "phoenix tail feather", wood = "holly", length = 11.0),
                    patronus = "stag",
                    hogwartsStaff = false,
                    hogwartsStudent = true,
                    actor = "Daniel Radcliffe",
                    alternateActors = emptyList(),
                    alive = true,
                    image = "https://ik.imagekit.io/hpapi/harry.jpg"

                )
            )

            assertEquals(expectedResponse, actual)
        }
    }

    private fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
        val context = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as HogwartsApplication
        val inputStream = context.assets.open("networkResponses/$fileName")

        val source = inputStream.let { inputStream.source().buffer() }
        enqueue(
            MockResponse()
                .setResponseCode(code)
                .setBody(source.readString(StandardCharsets.UTF_8))
        )
    }
}