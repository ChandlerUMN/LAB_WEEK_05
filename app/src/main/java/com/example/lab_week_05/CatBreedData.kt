package com.example.lab_week_05.model

import com.squareup.moshi.Json

data class CatBreedData(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "name") val name: String
)

