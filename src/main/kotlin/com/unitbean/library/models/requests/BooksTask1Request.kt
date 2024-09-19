package com.unitbean.library.models.requests

import com.fasterxml.jackson.annotation.JsonProperty

data class BooksTask1Request(
    @JsonProperty("year_of_release")
    val yearOfRelease: Int,

    @JsonProperty("authors_count")
    val authorsCount: Int
)
