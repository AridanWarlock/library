package com.unitbean.library.api.controllers

import com.unitbean.library.api.services.CustomersService
import com.unitbean.library.models.responses.CustomerResponse
import com.unitbean.library.util.annotations.MobRestController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.UUID

@MobRestController("api/customers")
class CustomersController(
    private val customersService: CustomersService
) {

    @GetMapping
    fun getAll(): List<CustomerResponse> {
        return customersService.getAll()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: UUID): CustomerResponse? {
        return customersService.getById(id)
    }
}