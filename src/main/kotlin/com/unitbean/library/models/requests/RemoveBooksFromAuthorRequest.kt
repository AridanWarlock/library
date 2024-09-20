package com.unitbean.library.models.requests

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class RemoveBooksFromAuthorRequest(
    @JsonProperty("author_id")
    val authorId: UUID,

    @JsonProperty("book_ids")
    val bookIds: Set<UUID>
)
