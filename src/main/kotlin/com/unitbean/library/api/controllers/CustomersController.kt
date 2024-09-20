package com.unitbean.library.api.controllers

import com.unitbean.library.interfaces.ICustomersService
import com.unitbean.library.models.requests.*
import com.unitbean.library.models.responses.CustomerResponse
import com.unitbean.library.models.responses.TimeResponse
import com.unitbean.library.util.annotations.MobRestController
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.system.measureNanoTime

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
    fun getAllTask2(
        @RequestBody request: CustomersTask2Request
    ) = customersService.getAllTask2(request)

    @GetMapping("/task_2/time")
    fun getTimeTask2(
        @RequestBody request: CustomersTask2Request
    ): TimeResponse {
        val time = measureNanoTime {
            customersService.getAllTask2(request)
        }
        return TimeResponse(time)
    }

    @GetMapping("/task_2_native")
    fun getAllTask2Native(
        @RequestBody request: CustomersTask2Request
    ): List<CustomerResponse> {
        return customersService.getAllTask2Native(request)
    }

    @GetMapping("/task_2_native/time")
    fun getTimeTask2Native(
        @RequestBody request: CustomersTask2Request
    ): TimeResponse {
        val time = measureNanoTime {
            customersService.getAllTask2Native(request)
        }
        return TimeResponse(time)
    }

    @GetMapping("/task_3")
    fun getAllByTask3(
        @RequestBody request: CustomersTask3Request
    ) = customersService.getAllTask3(request)

    @GetMapping("/task_3/time")
    fun getTimeTask3(
        @RequestBody request: CustomersTask3Request
    ): TimeResponse {
        val time = measureNanoTime {
            customersService.getAllTask3(request)
        }
        return TimeResponse(time)
    }

    @GetMapping("/task_3_native")
    fun getAllTask2Native(
        @RequestBody request: CustomersTask3Request
    ): List<CustomerResponse> {
        return customersService.getAllTask3Native(request)
    }

    @GetMapping("/task_3_native/time")
    fun getTimeTask2(
        @RequestBody request: CustomersTask3Request
    ): TimeResponse {
        val time = measureNanoTime {
            customersService.getAllTask3Native(request)
        }
        return TimeResponse(time)
    }

    @PutMapping("/take_books")
    fun takeBooks(
        @RequestBody request: TakeBooksRequest
    ) = customersService.takeBooks(request)

    @PutMapping("/bring_back_books")
    fun putBooks(
        @RequestBody request: PutBooksRequest
    ) = customersService.bringBackBooks(request)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: UUID) = customersService.delete(id)
}