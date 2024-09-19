package com.unitbean.library.interfaces

import com.unitbean.library.models.requests.AuthorCreateRequest
import com.unitbean.library.models.responses.AuthorResponse
import org.springframework.http.ResponseEntity
import java.util.*

interface IAuthorsService {
    fun getAll(): List<AuthorResponse>
    fun getById(id: UUID): AuthorResponse

    fun create(request: AuthorCreateRequest): ResponseEntity<UUID>
}