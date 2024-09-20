package com.unitbean.library.interfaces

import com.unitbean.library.models.requests.AddBooksToAuthorRequest
import com.unitbean.library.models.requests.AuthorCreateRequest
import com.unitbean.library.models.requests.RemoveBooksFromAuthorRequest
import com.unitbean.library.models.responses.AuthorResponse
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface IAuthorsService {
    fun create(request: AuthorCreateRequest): ResponseEntity<AuthorResponse>

    fun getAll(): List<AuthorResponse>
    fun getById(id: UUID): AuthorResponse

    @Transactional
    fun addBooks(request: AddBooksToAuthorRequest): AuthorResponse
    @Transactional
    fun removeBooks(request: RemoveBooksFromAuthorRequest): AuthorResponse

    @Transactional
    fun delete(id: UUID): ResponseEntity<Void>
}