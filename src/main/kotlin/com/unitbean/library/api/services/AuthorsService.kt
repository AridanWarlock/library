package com.unitbean.library.api.services

import com.unitbean.library.db.entity.AuthorRepository
import com.unitbean.library.interfaces.IAuthorService
import com.unitbean.library.models.responses.AuthorResponse
import org.springframework.stereotype.Service

@Service
class AuthorsService(
    private val authorRepository: AuthorRepository
) : IAuthorService {
    fun getAll(): List<AuthorResponse> {
        return authorRepository.findAllByDeletedFalse().map { AuthorResponse.of(it) }
    }
}