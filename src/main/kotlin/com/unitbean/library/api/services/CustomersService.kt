package com.unitbean.library.api.services

import com.unitbean.library.db.entity.BooksRepository
import com.unitbean.library.db.entity.CustomerModel
import com.unitbean.library.db.entity.CustomersRepository
import com.unitbean.library.interfaces.ICustomersService
import com.unitbean.library.models.requests.CustomerCreateRequest
import com.unitbean.library.models.requests.CustomersTask2Request
import com.unitbean.library.models.requests.CustomersTask3Request
import com.unitbean.library.models.responses.CustomerResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class CustomersService(
    private val customersRepository: CustomersRepository,
    private val booksRepository: BooksRepository,
) : ICustomersService {
    override fun getAll(): List<CustomerResponse> {
        return customersRepository.findAllByIsDeletedIsFalse().map { CustomerResponse.of(it) }
    }

    override fun getById(id: UUID): CustomerResponse {
        val customer = customersRepository.findByIdOrNull(id)

        customer ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer not found")

        return CustomerResponse.of(customer)
    }

    override fun getAllByTask2(request: CustomersTask2Request): List<CustomerResponse> {
        return customersRepository.findAllByTask2(request.yearOfBirth).map { CustomerResponse.of(it) }
    }


    override fun getAllByTask2NativeQuery(request: CustomersTask2Request): List<CustomerResponse> {
        return customersRepository.findAllByTask2NativeQuery(request.yearOfBirth).map { CustomerResponse.of(it) }
    }

    override fun getAllByTask3(request: CustomersTask3Request): List<CustomerResponse> {
        return customersRepository.findAllByTask3(request.firstName).map { CustomerResponse.of(it) }
    }

    override fun getAllByTask3NativeQuery(request: CustomersTask3Request): List<CustomerResponse> {
        return customersRepository.findAllByTask3NativeQuery(request.firstName).map { CustomerResponse.of(it) }
    }

    override fun create(request: CustomerCreateRequest): ResponseEntity<UUID> {
        val books = booksRepository.findAllById(request.bookIds)

        val customer = request.run {
            CustomerModel(
                firstName = firstName,
                secondName = secondName,
                phone = phone,
                books = books.toMutableSet()
            )
        }
        val savedCustomer = customersRepository.save(customer)

        return ResponseEntity(savedCustomer.id!!, HttpStatus.CREATED)
    }
}