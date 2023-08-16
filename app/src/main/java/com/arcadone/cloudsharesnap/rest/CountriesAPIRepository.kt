package com.arcadone.cloudsharesnap.rest

import com.arcadone.cloudsharesnap.domain.model.Country

interface CountriesAPIRepository {
    suspend fun getCountries(): List<Country>?
}

class CountriesAPIRepositoryImpl(
    private val countryService: CountryService,
) : CountriesAPIRepository {

    override suspend fun getCountries(): List<Country>? {
        val response = countryService.getCountries()
        return response.body()?.map { it.toDomain() }
    }

}
