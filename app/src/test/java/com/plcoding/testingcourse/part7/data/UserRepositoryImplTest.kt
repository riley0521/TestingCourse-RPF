package com.plcoding.testingcourse.part7.data

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import com.plcoding.testingcourse.part7.domain.Post
import com.plcoding.testingcourse.part7.domain.Profile
import com.plcoding.testingcourse.part7.domain.User
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

internal class UserRepositoryImplTest {

    private lateinit var repository: UserRepositoryImpl

    private lateinit var mockWebServer: MockWebServer
    private lateinit var userApi: UserApi

    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        userApi = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create()

        repository = UserRepositoryImpl(userApi)
    }

    @RepeatedTest(100)
    fun `Get profile, returns the correct Profile`() = runBlocking {

        val userJson = """
            {
              "id": "1",
              "username": "test"
            }
        """.trimIndent()

        val postsJson = """
            [
              {
                "id": "1",
                "userId": "1",
                "title": "Title 1",
                "body": "Sample Description 1"
              },
              {
                "id": "2",
                "userId": "1",
                "title": "Title 2",
                "body": "Sample Description 2"
              },
              {
                "id": "3",
                "userId": "1",
                "title": "Title 3",
                "body": "Sample Description 3"
              }
            ]
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(userJson)
        )

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(postsJson)
        )

        val profile = repository.getProfile("1")

        assertThat(profile.isSuccess).isTrue()

        val expectedProfile = Profile(
            user = User(
                id = "1",
                username = "test"
            ),
            posts = listOf(
                Post(id = "1", userId = "1", title = "Title 1", body = "Sample Description 1"),
                Post(id = "2", userId = "1", title = "Title 2", body = "Sample Description 2"),
                Post(id = "3", userId = "1", title = "Title 3", body = "Sample Description 3"),
            )
        )

        assertThat(profile.getOrNull()).isEqualTo(expectedProfile)
    }
}