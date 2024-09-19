package com.unitbean.library.models.responses

import com.fasterxml.jackson.annotation.JsonProperty
import com.unitbean.library.db.entity.AuthorModel
import java.io.Serializable
import java.util.UUID

data class AuthorResponse(
    val id: UUID,
    @JsonProperty("first_name")
    val firstName: String,
    @JsonProperty("second_name")
    val secondName: String,
    @JsonProperty("year_of_birth")
    val yearOfBirth: Int,
    @JsonProperty("book_ids")
    val bookIds: List<UUID>,
) : Serializable {
    companion object {
        fun of(author: AuthorModel) = AuthorResponse(
            id = author.id!!,
            firstName = author.firstName,
            secondName = author.secondName,
            yearOfBirth = author.yearOfBirth,
            bookIds = author.books.map { it.id!! }
        )
    }
}