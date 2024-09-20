package com.unitbean.library.api.services

import com.unitbean.library.db.entity.AuthorRepository
import com.unitbean.library.db.entity.BookModel
import com.unitbean.library.db.entity.BooksRepository
import com.unitbean.library.interfaces.IBooksService
import com.unitbean.library.models.requests.AddAuthorsToBookRequest
import com.unitbean.library.models.requests.BookCreateRequest
import com.unitbean.library.models.requests.BooksTask1Request
import com.unitbean.library.models.requests.RemoveAuthorsFromBookRequest
import com.unitbean.library.models.responses.BookResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class BooksService(
    private val booksRepository: BooksRepository,
    private val authorRepository: AuthorRepository,
) : IBooksService {
    override fun getAll(): List<BookResponse> {
        return booksRepository.findAllByIsDeletedIsFalse().map { BookResponse.of(it) }
    }

    override fun create(request: BookCreateRequest): ResponseEntity<UUID> {
        val authors = authorRepository.findAllById(request.authorIds)

        val book = request.run {
            BookModel(
                title = title,
                description = description,
                yearOfRelease = yearOfRelease,
                authors = authors.toMutableSet(),
            )
        }

        val savedBook = booksRepository.save(book)

        return ResponseEntity(savedBook.id!!, HttpStatus.CREATED)
    }

    override fun getById(id: UUID): BookResponse {
        val book = booksRepository.findByIdOrNull(id)

        book ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found")

        return BookResponse.of(book)
    }

    @Transactional
    override fun addAuthors(request: AddAuthorsToBookRequest): BookResponse {
        val book = booksRepository.findByIdOrNull(request.bookId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found")

        val authors = authorRepository.findAllById(request.authorIds).toSet()

        if (authors.isEmpty())
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Authors not found")

        authors.forEach {
            it.books.add(book)
        }
        authorRepository.saveAll(authors)

        book.authors.addAll(authors)

        val savedBook = booksRepository.save(book)

        return BookResponse.of(savedBook)
    }

    @Transactional
    override fun removeAuthors(request: RemoveAuthorsFromBookRequest): BookResponse {
        val book = booksRepository.findByIdOrNull(request.bookId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found")

        val authors = authorRepository.findAllById(request.authorIds).toSet()

        if (authors.isEmpty())
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Authors not found")

        authors.forEach {
            it.books.remove(book)
        }
        authorRepository.saveAll(authors)

        book.authors.removeAll(authors)

        val savedBook = booksRepository.save(book)

        return BookResponse.of(savedBook)
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