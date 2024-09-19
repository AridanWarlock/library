package com.unitbean.library.api.controllers

import com.unitbean.library.api.services.BooksService
import com.unitbean.library.models.responses.BookResponse
import com.unitbean.library.util.annotations.MobRestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.UUID

@MobRestController(value = "api/books")
class BooksController(
    private val booksService: BooksService
) {

    @GetMapping
    fun getAll() = booksService.getAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: UUID): BookResponse? {
        return booksService.getById(id)
    }
}