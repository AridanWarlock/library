package com.unitbean.library.api.services

import com.unitbean.library.db.entity.AuthorModel
import com.unitbean.library.db.entity.AuthorRepository
import com.unitbean.library.db.entity.BooksRepository
import com.unitbean.library.interfaces.IAuthorsService
import com.unitbean.library.models.requests.AuthorCreateRequest
import com.unitbean.library.models.responses.AuthorResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class AuthorsService(
    private val authorRepository: AuthorRepository,
    private val booksRepository: BooksRepository
) : IAuthorsService {
    override fun getAll(): List<AuthorResponse> {
        return authorRepository.findAllByIsDeletedFalse().map { AuthorResponse.of(it) }
    }

    override fun getById(id: UUID): AuthorResponse {
        val author = authorRepository.findByIdOrNull(id)

        author ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Author not found")

        return AuthorResponse.of(author)
    }

    override fun create(request: AuthorCreateRequest): ResponseEntity<UUID> {
        val books = booksRepository.findAllById(request.bookIds)

        val author = request.run {
            AuthorModel(
                firstName = firstName,
                secondName = secondName,
                yearOfBirth = yearOfBirth,
                books = books.toMutableSet()
            )
        }

        val savedAuthor = authorRepository.save(author)

        return ResponseEntity(savedAuthor.id!!, HttpStatus.CREATED)
    }
}