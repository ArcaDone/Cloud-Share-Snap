package com.arcadone.cloudsharesnap.usecase

import com.arcadone.cloudsharesnap.domain.GetCountriesUseCase
import com.arcadone.cloudsharesnap.domain.GetCountriesUseCaseImpl
import com.arcadone.cloudsharesnap.domain.model.onFailure
import com.arcadone.cloudsharesnap.domain.model.onSuccess
import com.arcadone.cloudsharesnap.mock.Mocks
import com.arcadone.cloudsharesnap.rest.CountriesAPIRepository
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * This class `GetCountriesUseCaseImplTest` is a JUnit test class for testing the behavior of the `GetCountriesUseCaseImpl` class.
 * Here's what is being done in this class:
 *
 * - The test class sets up a `GetCountriesUseCase` instance named `useCase` and a mocked `CountriesAPIRepository`.
 * - In the class initializer block (`init` block), Mockito annotations are opened to initialize the mocked dependencies.
 * - In the `setup()` method annotated with `@Before`, the `useCase` is created by passing the mocked `countriesAPIRepository`.
 * - The `Get Countries` test method (`Get Countries` is the test's description) tests the behavior of the `useCase.invoke()` function,
 *   which retrieves a list of countries. Inside the test method:
 *   - Mock behavior is set for the `countriesAPIRepository` to simulate that it will return the predefined `Mocks.countryList`.
 *   - The `useCase.invoke()` function is called with a dummy `Unit` parameter.
 *   - The result of the `invoke` operation is checked using the `.onSuccess` and `.onFailure` callbacks.
 *   - In the `.onSuccess` block, the response is matched against expected values using Hamcrest matchers. This is done to ensure
 *     that the response contains the expected data for the countries. Matchers are used to perform assertions on individual fields
 *     of the response objects.
 *   - In the `.onFailure` block, a simple `assert(false)` statement is used. This is a way to indicate that the test should fail
 *     if the `.onFailure` block is reached. However, this approach is not ideal because it doesn't provide clear information about
 *     the failure reason.
 * - Overall, this test class is verifying that the `GetCountriesUseCaseImpl` behaves as expected when retrieving a list of countries.
 *   It checks both the success scenario, where the data is correctly retrieved and matched, and the failure scenario, which should
 *   not be reached.
 */
class GetCountriesUseCaseImplTest {

    private lateinit var useCase: GetCountriesUseCase

    @Mock
    private lateinit var countriesAPIRepository: CountriesAPIRepository

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Before
    fun setup() = runBlocking {
        useCase = GetCountriesUseCaseImpl(countriesAPIRepository)
    }

    @Test
    fun `Get Countries`() = runBlocking<Unit> {
        whenever(countriesAPIRepository.getCountries()).thenReturn(Mocks.countryList)
        useCase.invoke(Unit)
            .onSuccess { response ->
                MatcherAssert.assertThat(response.size, CoreMatchers.equalTo(10))
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

                MatcherAssert.assertThat(response[2].iso, CoreMatchers.equalTo(12))
                MatcherAssert.assertThat(response[2].isoAlpha2, CoreMatchers.equalTo("DZ"))
                MatcherAssert.assertThat(response[2].isoAlpha3, CoreMatchers.equalTo("DZA"))
                MatcherAssert.assertThat(response[2].name, CoreMatchers.equalTo("Algeria"))
                MatcherAssert.assertThat(response[2].phonePrefix, CoreMatchers.equalTo("+213"))

                MatcherAssert.assertThat(response.last().iso, CoreMatchers.equalTo(450))
                MatcherAssert.assertThat(response.last().isoAlpha2, CoreMatchers.equalTo("MG"))
                MatcherAssert.assertThat(response.last().isoAlpha3, CoreMatchers.equalTo("MDG"))
                MatcherAssert.assertThat(response.last().name, CoreMatchers.equalTo("Madagascar"))
                MatcherAssert.assertThat(response.last().phonePrefix, CoreMatchers.equalTo("+261"))
            }
            .onFailure {
                assert(false)
            }
    }
}