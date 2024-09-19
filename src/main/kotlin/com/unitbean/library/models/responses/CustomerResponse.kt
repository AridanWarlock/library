package com.unitbean.library.models.responses

import com.fasterxml.jackson.annotation.JsonProperty
import com.unitbean.library.db.entity.CustomerModel
import java.io.Serializable
import java.util.UUID

data class CustomerResponse(
    val id: UUID,
    @JsonProperty("first_name")
    val firstName: String,
    @JsonProperty("second_name")
    val secondName: String,
    val phone: String,
    @JsonProperty("book_ids")
    val bookIds: List<UUID>
) : Serializable {
    companion object {
        fun of(customer: CustomerModel) = customer.run {
            CustomerResponse(
                id = id!!,
                firstName = firstName,
                secondName = secondName,
                phone = phone,
                bookIds = books.map { it.id!! }
            )
        }
    }
}