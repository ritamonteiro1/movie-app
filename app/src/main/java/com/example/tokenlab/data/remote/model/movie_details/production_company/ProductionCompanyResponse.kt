package com.example.tokenlab.data.remote.model.movie_details.production_company

import com.google.gson.annotations.SerializedName

data class ProductionCompanyResponse(
    val name: String?,
    @SerializedName("origin_country")
    val originCountry: String?,
    @SerializedName("logo_url")
    val logoUrl: String?
)
