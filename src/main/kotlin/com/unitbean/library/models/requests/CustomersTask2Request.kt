package com.unitbean.library.models.requests

import com.fasterxml.jackson.annotation.JsonProperty

data class CustomersTask2Request(
    @JsonProperty("year_of_birth")
    val yearOfBirth: Int
)
