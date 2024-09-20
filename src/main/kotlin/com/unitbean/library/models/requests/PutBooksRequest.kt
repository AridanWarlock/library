package com.unitbean.library.models.requests

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class PutBooksRequest(
    @JsonProperty("customer_id")
    val customerId: UUID,

    @JsonProperty("book_ids")
    val bookIds: List<UUID>
)
