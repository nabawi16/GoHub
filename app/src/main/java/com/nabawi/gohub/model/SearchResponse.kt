package com.nabawi.gohub.model

import com.squareup.moshi.Json

data class SearchResponse(
    @field:Json(name = "items")
    val items: List<UserEntity>
)
