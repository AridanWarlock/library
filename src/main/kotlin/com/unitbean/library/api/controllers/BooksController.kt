package com.unitbean.library.api.controllers

import com.unitbean.library.interfaces.IBooksService
import com.unitbean.library.models.requests.AddAuthorsToBookRequest
import com.unitbean.library.models.requests.BookCreateRequest
import com.unitbean.library.models.requests.BooksTask1Request
import com.unitbean.library.models.requests.RemoveAuthorsFromBookRequest
import com.unitbean.library.models.responses.BookResponse
import com.unitbean.library.models.responses.TimeResponse
import com.unitbean.library.util.annotations.MobRestController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.system.measureNanoTime

@MobRestController(value = "api/books")
class BooksController(
    private val booksService: IBooksService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody request: BookCreateRequest
    ): ResponseEntity<BookResponse> {
        return booksService.create(request)
    }

    @GetMapping
    fun getAll() = booksService.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: UUID) = booksService.getById(id)

    @GetMapping("/task_1")
    fun getAllTask1(
        @RequestBody request: BooksTask1Request
    ) = booksService.getAllTask1(request)

    @GetMapping("/task_1/time")
    fun getTimeTask1(
        @RequestBody request: BooksTask1Request
    ): TimeResponse {
        val time = measureNanoTime {
            booksService.getAllTask1(request)
        }

        return TimeResponse(time)
    }

    @GetMapping("/task_1_native")
    fun getAllTask1Native(
        @RequestBody request: BooksTask1Request
    ) = booksService.getAllTask1Native(request)

    @GetMapping("/task_1_native/time")
    fun getTimeTask1Native(
        @RequestBody request: BooksTask1Request
    ): TimeResponse {
        val time = measureNanoTime {
            booksService.getAllTask1Native(request)
        }

        return TimeResponse(time)
    }

    @PutMapping("/add_authors")
    fun addAuthors(
        @RequestBody request: AddAuthorsToBookRequest
    ) = booksService.addAuthors(request)

    @PutMapping("/remove_authors")
    fun removeAuthors(
        @RequestBody request: RemoveAuthorsFromBookRequest
    ) = booksService.removeAuthors(request)

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable("id") id: UUID) = booksService.delete(id)
}