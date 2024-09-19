package com.unitbean.library.interfaces

import com.unitbean.library.models.requests.BooksTask1Request
import com.unitbean.library.models.responses.BookResponse
import java.util.UUID

interface IBooksService {
    fun getAll(): List<BookResponse>
    fun getById(id: UUID): BookResponse?
    fun getAllByTask1(request: BooksTask1Request): List<BookResponse>
    fun getAllByTask1NativeQuery(request: BooksTask1Request): List<BookResponse>
}