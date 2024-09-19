package com.unitbean.library.api.services

import com.unitbean.library.db.entity.AuthorRepository
import com.unitbean.library.interfaces.IAuthorsService
import com.unitbean.library.models.responses.AuthorResponse
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthorsService(
    private val authorRepository: AuthorRepository
) : IAuthorsService {
    override fun getAll(): List<AuthorResponse> {
        return authorRepository.findAllByIsDeletedFalse().map { AuthorResponse.of(it) }
    }

    override fun getById(id: UUID): AuthorResponse? {
        return authorRepository.findById(id).map { AuthorResponse.of(it) }.orElse(null)
    }
}