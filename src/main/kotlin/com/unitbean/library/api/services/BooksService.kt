package com.unitbean.library.api.services

import com.unitbean.library.db.entity.BooksRepository
import com.unitbean.library.interfaces.IBooksService
import com.unitbean.library.models.requests.BooksTask1Request
import com.unitbean.library.models.responses.BookResponse
import org.springframework.stereotype.Service

@Service
class BooksService(
    val booksRepository: BooksRepository
) : IBooksService {
    override fun getAllBooks(): List<BookResponse> {
        return booksRepository.findAllByDeletedIsFalse().map { BookResponse.of(it) }
    }

    override fun getAllByTask1(request: BooksTask1Request): List<BookResponse> {
        return booksRepository.findAllByTask1(request.yearOfRelease, request.authorsCount)
            .map { BookResponse.of(it) }
    }

    override fun getAllByTask1NativeQuery(request: BooksTask1Request): List<BookResponse> {
        return booksRepository.findAllByTask1NativeQuery(request.yearOfRelease, request.authorsCount)
            .map { BookResponse.of(it) }
    }

}