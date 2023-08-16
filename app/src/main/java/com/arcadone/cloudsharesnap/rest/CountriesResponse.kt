package com.arcadone.cloudsharesnap.rest

import com.arcadone.cloudsharesnap.domain.model.Country
import com.fasterxml.jackson.annotation.JsonProperty

data class CountriesResponse(
    @JsonProperty("iso")
    val iso: Int,
    @JsonProperty("isoAlpha2")
    val isoAlpha2: String,
    @JsonProperty("isoAlpha3")
    val isoAlpha3: String,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("phonePrefix")
    val phonePrefix: String,
) {
    fun toDomain(): Country {
        return Country(
            iso = iso,
            isoAlpha2 = isoAlpha2,
            isoAlpha3 = isoAlpha3,
            name = name,
            phonePrefix = phonePrefix
        )
    }
}