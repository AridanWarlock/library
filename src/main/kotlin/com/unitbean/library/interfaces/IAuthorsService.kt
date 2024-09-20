package com.unitbean.library.interfaces

import com.unitbean.library.models.requests.AddBooksToAuthorRequest
import com.unitbean.library.models.requests.AuthorCreateRequest
import com.unitbean.library.models.requests.RemoveBooksFromAuthorRequest
import com.unitbean.library.models.responses.AuthorResponse
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface IAuthorsService {
    fun getAll(): List<AuthorResponse>
    fun getById(id: UUID): AuthorResponse

    fun create(request: AuthorCreateRequest): ResponseEntity<UUID>

    @Transactional
    fun addBooks(request: AddBooksToAuthorRequest): AuthorResponse
    @Transactional
    fun removeBooks(request: RemoveBooksFromAuthorRequest): AuthorResponse
}