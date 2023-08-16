package com.arcadone.cloudsharesnap.rest

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
interface CountryService {

    @GET("geographics/countries/")
    suspend fun getCountries(
        @Header("x-api-key") xApikey : String = "AIzaSyCccmdkjGe_9Yt-INL2rCJTNgoS4CXsRDc",
    ): Response<List<CountriesResponse>>
}
