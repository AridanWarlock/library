package com.unitbean.library.api.controllers

import com.unitbean.library.api.services.AuthorsService
import com.unitbean.library.models.responses.AuthorResponse
import com.unitbean.library.util.annotations.MobRestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.*

@MobRestController(value = "api/authors")
class AuthorsController (
    private val authorsService: AuthorsService
){

    @GetMapping
    fun getAll(): List<AuthorResponse> {
        return authorsService.getAll()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: UUID): AuthorResponse? {
        return authorsService.getById(id)
    }
}