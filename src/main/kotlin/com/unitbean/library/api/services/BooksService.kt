package com.unitbean.library.api.services

import com.unitbean.library.db.entity.BooksRepository
import com.unitbean.library.interfaces.IBooksService
import com.unitbean.library.models.requests.BooksTask1Request
import com.unitbean.library.models.responses.BookResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class BooksService(
    val booksRepository: BooksRepository
) : IBooksService {
    override fun getAll(): List<BookResponse> {
        return booksRepository.findAllByIsDeletedIsFalse().map { BookResponse.of(it) }
    }

    override fun getById(id: UUID): BookResponse? {
        return booksRepository.findById(id).map { BookResponse.of(it) }.orElse(null)
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