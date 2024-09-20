package com.unitbean.library.api.services

import com.unitbean.library.db.entity.AuthorModel
import com.unitbean.library.db.entity.AuthorRepository
import com.unitbean.library.db.entity.BooksRepository
import com.unitbean.library.interfaces.IAuthorsService
import com.unitbean.library.models.requests.AddBooksToAuthorRequest
import com.unitbean.library.models.requests.AuthorCreateRequest
import com.unitbean.library.models.requests.RemoveBooksFromAuthorRequest
import com.unitbean.library.models.responses.AuthorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class AuthorsService(
    private val authorRepository: AuthorRepository,
    private val booksRepository: BooksRepository
) : IAuthorsService {
    override fun getAll(): List<AuthorResponse> {
        return authorRepository.findAllByIsDeleted(false)
            .map { AuthorResponse.of(it) }
    }

    override fun getById(id: UUID): AuthorResponse {
        val author = authorRepository.findByIdAndIsDeleted(id, false)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found")

        return AuthorResponse.of(author)
    }

    @Transactional
    override fun addBooks(request: AddBooksToAuthorRequest): AuthorResponse {
        val author = authorRepository.findByIdAndIsDeleted(request.authorId, false)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found")

        val books = booksRepository.findAllByIdInAndIsDeleted(request.bookIds, false)
            .toSet()

        if (books.isEmpty())
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Books by ids not found")

        books.forEach { book ->
            book.authors.add(author)
        }

        val savedBooks = booksRepository.saveAll(books)

        author.books.addAll(savedBooks)
        val savedAuthor = authorRepository.save(author)

        return AuthorResponse.of(savedAuthor)
    }

    @Transactional
    override fun removeBooks(request: RemoveBooksFromAuthorRequest): AuthorResponse {
        val author = authorRepository.findByIdAndIsDeleted(request.authorId, false)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found")

        val books = booksRepository.findAllByIdInAndIsDeleted(request.bookIds, false)
            .toMutableSet()

        if (books.isEmpty())
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Books not found")

        books.forEach { book ->
            book.authors.remove(author)
        }
        booksRepository.saveAll(books)

        author.books.removeAll(books)

        val savedAuthor = authorRepository.save(author)

        return AuthorResponse.of(savedAuthor)
    }

    override fun create(request: AuthorCreateRequest): ResponseEntity<AuthorResponse> {
        val books = booksRepository.findAllByIdInAndIsDeleted(request.bookIds, false)
            .toMutableSet()

        val author = request.run {
            AuthorModel(
                firstName = firstName,
                secondName = secondName,
                yearOfBirth = yearOfBirth,
                books = books
            )
        }

        val savedAuthor = authorRepository.save(author)

        return ResponseEntity(
            AuthorResponse.of(savedAuthor),
            HttpStatus.CREATED
        )
    }

    @Transactional
    override fun delete(id: UUID): ResponseEntity<Void> {
        val author = authorRepository.findByIdAndIsDeleted(id, false)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found")

        author.isDeleted = true
        authorRepository.save(author)

        return ResponseEntity.ok().build()
    }
}