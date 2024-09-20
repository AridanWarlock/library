package com.unitbean.library.models.requests

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class AuthorCreateRequest(
    @JsonProperty("first_name")
    val firstName: String,

    @JsonProperty("second_name")
    val secondName: String,

    @JsonProperty("year_of_birth")
    val yearOfBirth: Int,

    @JsonProperty("book_ids")
    val bookIds: List<UUID> = listOf(),
)
