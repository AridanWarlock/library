package com.unitbean.library.models.requests

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class RemoveAuthorsFromBookRequest(
    @JsonProperty("book_id")
    val bookId: UUID,

    @JsonProperty("author_ids")
    val authorIds: Set<UUID>
)
