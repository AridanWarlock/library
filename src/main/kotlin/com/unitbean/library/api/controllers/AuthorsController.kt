package com.unitbean.library.api.controllers

import com.unitbean.library.api.services.AuthorsService
import com.unitbean.library.interfaces.IAuthorsService
import com.unitbean.library.models.requests.AuthorCreateRequest
import com.unitbean.library.models.requests.CustomerCreateRequest
import com.unitbean.library.models.responses.AuthorResponse
import com.unitbean.library.util.annotations.MobRestController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

//@CrossOrigin
//@MobRestController(value = "api/authors")
@RestController
@RequestMapping("/api/authors")
class AuthorsController (
    private val authorsService: IAuthorsService
){

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody request: AuthorCreateRequest
    ): ResponseEntity<UUID> {
        return authorsService.create(request)
    }


    @GetMapping
    fun getAll(): List<AuthorResponse> {
        return authorsService.getAll()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: UUID): AuthorResponse? {
        return authorsService.getById(id)
    }
}