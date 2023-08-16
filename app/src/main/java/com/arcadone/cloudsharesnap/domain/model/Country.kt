package com.arcadone.cloudsharesnap.domain.model

data class Country(
    val iso: Int,
    val isoAlpha2: String,
    val isoAlpha3: String,
    val name: String,
    val phonePrefix: String,
)