package com.unitbean.library.models.requests

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.util.UUID

data class BookCreateRequest(
    val title: String,
    val description: String,
    @JsonProperty("author_ids")
    val authorIds: List<UUID>,
    @JsonProperty("year_of_release")
    val yearOfRelease: Int
) : Serializable
