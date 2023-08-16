package com.arcadone.cloudsharesnap.viewModel

import com.arcadone.cloudsharesnap.domain.GetCountriesUseCase
import com.arcadone.cloudsharesnap.domain.model.ApiResult
import com.arcadone.cloudsharesnap.mock.Mocks
import com.arcadone.cloudsharesnap.networkconnection.NetworkConnection
import com.arcadone.cloudsharesnap.presentation.homescreen.HomeUiState
import com.arcadone.cloudsharesnap.presentation.homescreen.HomeViewModel
import com.nhaarman.mockitokotlin2.anyOrNull
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * This class HomeViewModelTest is a JUnit test class for testing the behavior of the
 * HomeViewModel class. Here's what is being done in this class:
 *
 * The HomeViewModelTest class extends BaseViewModelTest<HomeViewModel>,
 * which implies that it is inheriting some common testing setup from the parent class.
 *
 * Mocks are created for two dependencies: getCountriesUseCase and networkConnection.
 * These mock dependencies are used to simulate the behavior of the actual dependencies in the ViewModel.
 *
 * The setupTest() method is overridden from the parent class. In this method, Mockito annotations
 * are opened (MockitoAnnotations.openMocks(this)) to initialize the mocked dependencies.
 * Then, an instance of HomeViewModel is created using these mocked dependencies.
 *
 * The first test method, getCountries success - ShowSuccess state, is testing the scenario where
 * the getCountries use case returns a success result. Inside the test method:
 *
 * A mock response is created (mockResponse) using some predefined data (possibly from Mocks.countryList).
 * This represents the data that the use case would return upon success.
 * Mock behavior is set using when to simulate that the network is online and
 * the getCountriesUseCase will return a successful result with the mockResponse.
 *
 * The getCountries method of the ViewModel is invoked using viewModel.getCountries().
 * The uiState of the ViewModel is then checked, and it's verified that the actual data received
 * (actualCountries) matches the expected data (mockResponse).
 * The second test method, getCountries error - ShowError state,
 * tests the scenario where the getCountries use case returns an error.
 *
 * Inside the test method:
 *
 * Mock behavior is set to simulate that the getCountriesUseCase will return an error result with an exception.
 * Network connectivity is simulated as being online.
 * The getCountries method of the ViewModel is invoked.
 * The uiState of the ViewModel is checked, and it's verified that the actual error message received
 * (message) matches the exception's error message.
 * These test methods are used to ensure that the ViewModel behaves correctly in different scenarios:
 * when the use case returns a success and when it returns an error.
 * The test methods use mock dependencies to control the behavior of the use case and
 * network connection, allowing for isolated testing of the ViewModel's logic.
 */

class HomeViewModelTest : BaseViewModelTest<HomeViewModel>() {

    @Mock
    lateinit var getCountriesUseCase: GetCountriesUseCase

    @Mock
    lateinit var networkConnection: NetworkConnection

    override fun setupTest() {
        MockitoAnnotations.openMocks(this)
        viewModel = HomeViewModel(
            getCountriesUseCase = getCountriesUseCase,
            networkConnection = networkConnection
        )
    }

    @Test
    fun `getCountries success - ShowSuccess state`() = runBlocking {
        val mockResponse = Mocks.countryList.toMutableList()

        `when`(networkConnection.isOnline()).thenReturn(true)
        `when`(getCountriesUseCase.invoke(anyOrNull())).thenReturn(ApiResult.success(mockResponse))

        viewModel.getCountries().join()

        val actualCountries = viewModel.countries.value

        assertEquals(mockResponse, actualCountries)

    }

    @Test
    fun `getCountries error - ShowError state`() = runBlocking {
        `when`(getCountriesUseCase.invoke(anyOrNull())).thenReturn(ApiResult.error(exception))
        `when`(networkConnection.isOnline()).thenReturn(true)

        viewModel.getCountries().join()

        val message = (viewModel.uiState.value as? HomeUiState.ShowError)?.errorMessage

        assertEquals(exception.message, message)

    }

}