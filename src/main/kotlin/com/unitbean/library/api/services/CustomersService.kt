package com.unitbean.library.api.services

import com.unitbean.library.db.entity.BooksRepository
import com.unitbean.library.db.entity.CustomerModel
import com.unitbean.library.db.entity.CustomersRepository
import com.unitbean.library.interfaces.ICustomersService
import com.unitbean.library.models.requests.*
import com.unitbean.library.models.responses.CustomerResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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
        val customer = customersRepository.findByIdAndIsDeletedIsFalse(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")

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
        val books = booksRepository.findAllByIdAndIsDeletedIsFalse(request.bookIds)
            .toMutableSet()

        val customer = request.run {
            CustomerModel(
                firstName = firstName,
                secondName = secondName,
                phone = phone,
                books = books
            )
        }
        val savedCustomer = customersRepository.save(customer)

        return ResponseEntity(savedCustomer.id!!, HttpStatus.CREATED)
    }

    override fun putBooks(request: BringBackBooksRequest): CustomerResponse {
        val customer = customersRepository.findByIdAndIsDeletedIsFalse(request.customerId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")

        val booksOnCustomer = customer.books.map { it.id!! }
        val books = booksRepository.findAllByIdAndIsDeletedIsFalse(booksOnCustomer)
            .toSet()

        if (books.isEmpty())
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Books not found")

        books.forEach {
            it.customer = null
        }

        booksRepository.saveAll(books)

        customer.books.removeAll(books)

        val savedCustomer = customersRepository.save(customer)


        return CustomerResponse.of(savedCustomer)
    }

    @Transactional
    override fun takeBooks(request: TakeBooksRequest): CustomerResponse {
        val customer = customersRepository.findByIdAndIsDeletedIsFalse(request.customerId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")

        val books = booksRepository.findAllByIdAndIsDeletedIsFalseAndCustomerIsNull(request.bookIds.toList())
            .toSet()

        if (books.isEmpty())
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Books not found")

        books.forEach {
            it.customer = customer
        }

        booksRepository.saveAll(books)

        customer.books.addAll(books)

        val savedCustomer = customersRepository.save(customer)


        return CustomerResponse.of(savedCustomer)
    }

    @Transactional
    override fun delete(id: UUID): ResponseEntity<Void> {
        val customer = customersRepository.findByIdAndIsDeletedIsFalse(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")

        val books = booksRepository.findAllByIdAndIsDeletedIsFalse(
            customer.books.map { it.id!! }
        )

        books.forEach {
            it.customer = null
        }
        booksRepository.saveAll(books)

        customer.isDeleted = true
        customersRepository.save(customer)

        return ResponseEntity.ok().build()
    }
}