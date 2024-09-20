package com.unitbean.library.api.controllers

import com.unitbean.library.interfaces.ICustomersService
import com.unitbean.library.models.requests.*
import com.unitbean.library.models.responses.CustomerResponse
import com.unitbean.library.util.annotations.MobRestController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@MobRestController("api/customers")
class CustomersController(
    private val customersService: ICustomersService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody request: CustomerCreateRequest
    ): ResponseEntity<CustomerResponse> {
        return customersService.create(request)
    }

    @GetMapping
    fun getAll(): List<CustomerResponse> {
        return customersService.getAll()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: UUID): CustomerResponse? {
        return customersService.getById(id)
    }

    @GetMapping("/task_2")
    fun getAllByTask2(
        @RequestBody request: CustomersTask2Request
    ) = customersService.getAllTask2(request)

    @GetMapping("/task_2_native")
    fun getAllByTask2Native(
        @RequestBody request: CustomersTask2Request
    ): List<CustomerResponse> {
        return customersService.getAllTask2NativeQuery(request)
    }

    @GetMapping("/task_3")
    fun getAllByTask3(
        @RequestBody request: CustomersTask3Request
    ) = customersService.getAllTask3(request)

    @GetMapping("/task_3_native")
    fun getAllByTask2Native(
        @RequestBody request: CustomersTask3Request
    ): List<CustomerResponse> {
        return customersService.getAllByTask3NativeQuery(request)
    }

    @PutMapping("/put_books")
    fun putBooks(
        @RequestBody request: PutBooksRequest
    ) = customersService.bringBackBooks(request)

    @PutMapping("/take_books")
    fun takeBooks(
        @RequestBody request: TakeBooksRequest
    ) = customersService.takeBooks(request)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: UUID) = customersService.delete(id)
}