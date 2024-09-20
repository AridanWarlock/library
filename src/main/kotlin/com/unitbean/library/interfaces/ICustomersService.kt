package com.unitbean.library.interfaces

import com.unitbean.library.models.requests.*
import com.unitbean.library.models.responses.CustomerResponse
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

interface ICustomersService {
    fun getAll(): List<CustomerResponse>
    fun getById(id: UUID): CustomerResponse

    fun getAllTask2(request: CustomersTask2Request): List<CustomerResponse>
    fun getAllTask2NativeQuery(request: CustomersTask2Request): List<CustomerResponse>

    fun getAllTask3(request: CustomersTask3Request): List<CustomerResponse>
    fun getAllByTask3NativeQuery(request: CustomersTask3Request): List<CustomerResponse>

    fun create(request: CustomerCreateRequest): ResponseEntity<CustomerResponse>

    @Transactional
    fun takeBooks(request: TakeBooksRequest): CustomerResponse
    @Transactional
    fun bringBackBooks(request: PutBooksRequest): CustomerResponse

    @Transactional
    fun delete(id: UUID): ResponseEntity<Void>
}