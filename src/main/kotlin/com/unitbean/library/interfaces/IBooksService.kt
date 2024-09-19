package com.unitbean.library.interfaces

import com.unitbean.library.models.requests.BooksTask1Request
import com.unitbean.library.models.responses.BookResponse

interface IBooksService {
    fun getAllBooks(): List<BookResponse>
    fun getAllByTask1(request: BooksTask1Request): List<BookResponse>
    fun getAllByTask1NativeQuery(request: BooksTask1Request): List<BookResponse>
}