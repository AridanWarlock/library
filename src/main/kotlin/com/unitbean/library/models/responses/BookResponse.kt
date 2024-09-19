package com.unitbean.library.models.responses

import com.fasterxml.jackson.annotation.JsonProperty
import com.unitbean.library.db.entity.BookModel
import java.io.Serializable
import java.util.*

data class BookResponse(
    val id: UUID,
    val title: String,
    val description: String,
    @JsonProperty("author_ids")
    val authorIds: List<UUID>,
    @JsonProperty("year_of_release")
    val yearOfRelease: Int,
    @JsonProperty("customer_id")
    val customerId: UUID? = null,
) : Serializable {
    companion object {
        fun of(book: BookModel) = BookResponse(
            id = book.id!!,
            title = book.title,
            description = book.description,
            yearOfRelease = book.yearOfRelease,
            authorIds = book.authors.map { it.id!! },
            customerId = book.customer?.id
        )
    }
}
