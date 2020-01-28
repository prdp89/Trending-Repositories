package com.gojek.trendingrepo.api

import com.gojek.trendingrepo.datasource.service.ApiInterface
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers
import org.hamcrest.core.IsNull
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(JUnit4::class)
class ApiInterfaceTest {

    private lateinit var service: ApiInterface
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun getRepos() {
        enqueueResponse("contributors.json")
        val repos =
            service.getTrendingRepositories().execute().body()

        val request = mockWebServer.takeRequest()
        Assert.assertThat(request.path, CoreMatchers.`is`("/repositories"))

        Assert.assertThat(repos?.size, CoreMatchers.`is`(24))

        val repo = repos!![0]
        Assert.assertThat(repo.author, CoreMatchers.`is`("trimstray"))

        Assert.assertThat(repo.avatar, IsNull.notNullValue())

        Assert.assertThat(repo.stars, CoreMatchers.notNullValue())

        Assert.assertThat(repo.url, CoreMatchers.`is`("https://github.com/trimstray/the-book-of-secret-knowledge"))
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }
}