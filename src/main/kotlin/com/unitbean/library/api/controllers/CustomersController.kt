package com.unitbean.library.api.controllers

import com.unitbean.library.api.services.CustomersService
import com.unitbean.library.interfaces.ICustomersService
import com.unitbean.library.models.requests.CustomerCreateRequest
import com.unitbean.library.models.requests.CustomersTask2Request
import com.unitbean.library.models.requests.CustomersTask3Request
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
    ): ResponseEntity<UUID> {
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
    fun getAllByTask2(request: CustomersTask2Request) = customersService.getAllByTask2(request)

    @GetMapping("/task_2_native")
    fun getAllByTask2Native(request: CustomersTask2Request): List<CustomerResponse> {
        return customersService.getAllByTask2NativeQuery(request)
    }

    @GetMapping("/task_3")
    fun getAllByTask3(request: CustomersTask3Request) = customersService.getAllByTask3(request)

    @GetMapping("/task_3_native")
    fun getAllByTask2Native(request: CustomersTask3Request): List<CustomerResponse> {
        return customersService.getAllByTask3NativeQuery(request)
    }

}