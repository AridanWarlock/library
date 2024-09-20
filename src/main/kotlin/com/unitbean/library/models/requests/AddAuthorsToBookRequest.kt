package com.unitbean.library.models.requests

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class AddAuthorsToBookRequest(
    @JsonProperty("book_id")
    val bookId: UUID,

    @JsonProperty("author_ids")
    val authorIds: List<UUID>
)
