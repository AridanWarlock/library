package com.unitbean.library.api.controllers

import com.unitbean.library.api.services.BooksService
import com.unitbean.library.interfaces.IBooksService
import com.unitbean.library.models.requests.AuthorCreateRequest
import com.unitbean.library.models.requests.BookCreateRequest
import com.unitbean.library.models.requests.BooksTask1Request
import com.unitbean.library.models.responses.BookResponse
import com.unitbean.library.util.annotations.MobRestController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@MobRestController(value = "api/books")
class BooksController(
    private val booksService: IBooksService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody request: BookCreateRequest
    ): ResponseEntity<UUID> {
        return booksService.create(request)
    }

    @GetMapping
    fun getAll() = booksService.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: UUID): BookResponse? {
        return booksService.getById(id)
    }

    @GetMapping("/task_1")
    fun getAllByTask1(request: BooksTask1Request) = booksService.getAllByTask1(request)

    @GetMapping("/task_1_native")
    fun getAllByTask1Native(request: BooksTask1Request) = booksService.getAllByTask1NativeQuery(request)
}