package com.unitbean.library.interfaces

import com.unitbean.library.models.responses.AuthorResponse
import java.util.*

interface IAuthorsService {
    fun getAll(): List<AuthorResponse>
    fun getById(id: UUID): AuthorResponse?
}