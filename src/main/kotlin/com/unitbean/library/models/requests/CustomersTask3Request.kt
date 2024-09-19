package com.unitbean.library.models.requests

import com.fasterxml.jackson.annotation.JsonProperty

data class CustomersTask3Request(
    @JsonProperty("first_name")
    val firstName: String,
)
