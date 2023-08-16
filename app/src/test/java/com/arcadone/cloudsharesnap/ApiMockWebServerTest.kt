package com.arcadone.cloudsharesnap

import com.arcadone.cloudsharesnap.rest.CountryService
import com.arcadone.cloudsharesnap.rest.CountriesAPIRepository
import com.arcadone.cloudsharesnap.rest.CountriesAPIRepositoryImpl
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This class, `ApiMockWebServerTest`, is a JUnit test class responsible for testing the behavior of API interactions using a mock web server.
 * Here's what is being done in this class:
 *
 * - The test class sets up instances of `CountriesAPIRepository`, `MockWebServer`, and mocks `CountryService`.
 * - In the `setup()` method annotated with `@Before`, Mockito annotations are opened to initialize the mocked dependencies.
 *   The mock web server is started on a specific port (8080).
 *   The `countryService` is configured to use the mock web server's URL as the base URL and GsonConverterFactory for JSON conversion.
 *   An instance of `CountriesAPIRepositoryImpl` is created using the configured `countryService`.
 * - The `tearDown()` method annotated with `@After` ensures that the mock web server is shut down after each test.
 * - The `getCountriesTest()` test method is used to test the `getCountries` method of the `countriesAPIRepository`.
 *   A mock JSON response is enqueued in the mock web server.
 *   The `getCountries` method is called, and its response is retrieved.
 *   Hamcrest matchers are used to assert that the response contains the expected number of items and that the first item's fields
 *   match the expected values.
 *
 */
class ApiMockWebServerTest {

    private lateinit var countriesAPIRepository: CountriesAPIRepository
    private lateinit var mockWebServer: MockWebServer

    @Mock
    lateinit var countryService: CountryService

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        mockWebServer = MockWebServer()
        mockWebServer.start(8080)

        countryService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountryService::class.java)

        countriesAPIRepository = CountriesAPIRepositoryImpl(countryService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getCountries using Mock Web Server`() = runBlocking {
        mockWebServer.enqueue(MockResponseLoader.buildMockJsonResponse("countries.json"))

        val response = countriesAPIRepository.getCountries()

        MatcherAssert.assertThat(response!!.size, CoreMatchers.equalTo(225))
        MatcherAssert.assertThat(response.first().iso, CoreMatchers.equalTo(248))
        MatcherAssert.assertThat(response.first().isoAlpha2, CoreMatchers.equalTo("AX"))
        MatcherAssert.assertThat(response.first().isoAlpha3, CoreMatchers.equalTo("ALA"))
        MatcherAssert.assertThat(
            response.first().name,
            CoreMatchers.equalTo("Aland Islands")
        )
        MatcherAssert.assertThat(
            response.first().phonePrefix,
            CoreMatchers.equalTo("+358-18")
        )
    }
}
