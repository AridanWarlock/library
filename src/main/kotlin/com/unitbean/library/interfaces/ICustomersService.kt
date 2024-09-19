package com.unitbean.library.interfaces

import com.unitbean.library.models.requests.CustomerCreateRequest
import com.unitbean.library.models.requests.CustomersTask2Request
import com.unitbean.library.models.requests.CustomersTask3Request
import com.unitbean.library.models.responses.CustomerResponse
import org.springframework.http.ResponseEntity
import java.util.UUID

interface ICustomersService {
    fun getAll(): List<CustomerResponse>
    fun getById(id: UUID): CustomerResponse

    fun getAllByTask2(request: CustomersTask2Request): List<CustomerResponse>
    fun getAllByTask2NativeQuery(request: CustomersTask2Request): List<CustomerResponse>

    fun getAllByTask3(request: CustomersTask3Request): List<CustomerResponse>
    fun getAllByTask3NativeQuery(request: CustomersTask3Request): List<CustomerResponse>

    fun create(request: CustomerCreateRequest): ResponseEntity<UUID>
}