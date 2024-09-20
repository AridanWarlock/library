package com.unitbean.library.models.requests

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class CustomerCreateRequest(
    @JsonProperty("first_name")
    val firstName: String,

    @JsonProperty("second_name")
    val secondName: String,

    @JsonProperty("phone")
    val phone: String,

    @JsonProperty("book_ids")
    val bookIds: List<UUID> = listOf(),
)