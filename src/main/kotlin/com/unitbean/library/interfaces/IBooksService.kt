package com.unitbean.library.interfaces

import com.unitbean.library.models.requests.AddAuthorsToBookRequest
import com.unitbean.library.models.requests.BookCreateRequest
import com.unitbean.library.models.requests.BooksTask1Request
import com.unitbean.library.models.requests.RemoveAuthorsFromBookRequest
import com.unitbean.library.models.responses.BookResponse
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

interface IBooksService {
    fun create(request: BookCreateRequest): ResponseEntity<BookResponse>

    fun getAll(): List<BookResponse>
    fun getById(id: UUID): BookResponse
    fun getAllTask1(request: BooksTask1Request): List<BookResponse>
    fun getAllTask1Native(request: BooksTask1Request): List<BookResponse>

    @Transactional
    fun addAuthors(request: AddAuthorsToBookRequest): BookResponse
    @Transactional
    fun removeAuthors(request: RemoveAuthorsFromBookRequest): BookResponse

    @Transactional
    fun delete(id: UUID): ResponseEntity<Void>
}