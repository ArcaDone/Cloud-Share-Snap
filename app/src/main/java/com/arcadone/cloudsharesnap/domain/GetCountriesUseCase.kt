package com.arcadone.cloudsharesnap.domain

import com.arcadone.cloudsharesnap.domain.model.ApiResult
import com.arcadone.cloudsharesnap.domain.model.Country
import com.arcadone.cloudsharesnap.rest.CountriesAPIRepository
import com.arcadone.cloudsharesnap.rest.RestUseCase
import javax.inject.Inject

interface GetCountriesUseCase {
    suspend fun invoke(params: Unit): ApiResult<List<Country>>
}

class GetCountriesUseCaseImpl @Inject constructor(
    private val countriesAPIRepository: CountriesAPIRepository
) : RestUseCase<Unit, List<Country>>(), GetCountriesUseCase {

    override suspend fun execute(params: Unit): ApiResult<List<Country>> {
        val response = mutableListOf<Country>()
        countriesAPIRepository.getCountries()?.let { response.addAll(it) }
        return ApiResult.success(response)
    }

}
