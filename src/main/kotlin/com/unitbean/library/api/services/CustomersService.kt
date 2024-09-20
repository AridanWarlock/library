package com.unitbean.library.api.services

import com.unitbean.library.db.entity.BooksRepository
import com.unitbean.library.db.entity.CustomerModel
import com.unitbean.library.db.entity.CustomersRepository
import com.unitbean.library.interfaces.ICustomersService
import com.unitbean.library.models.requests.*
import com.unitbean.library.models.responses.CustomerResponse
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
        return customersRepository.findAllByIsDeleted(false)
            .map { CustomerResponse.of(it) }
    }

    override fun getById(id: UUID): CustomerResponse {
        val customer = customersRepository.findByIdAndIsDeleted(id, false)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")

        return CustomerResponse.of(customer)
    }

    override fun getAllTask2(request: CustomersTask2Request): List<CustomerResponse> {
        return customersRepository.findAllTask2(request.yearOfBirth)
            .map { CustomerResponse.of(it) }
    }


    override fun getAllTask2NativeQuery(request: CustomersTask2Request): List<CustomerResponse> {
        return customersRepository.findAllTask2Native(request.yearOfBirth)
            .map { CustomerResponse.of(it) }
    }

    override fun getAllTask3(request: CustomersTask3Request): List<CustomerResponse> {
        return customersRepository.findAllByTask3(request.firstName)
            .map { CustomerResponse.of(it) }
    }

    override fun getAllByTask3NativeQuery(request: CustomersTask3Request): List<CustomerResponse> {
        return customersRepository.findAllTask3Native(request.firstName)
            .map { CustomerResponse.of(it) }
    }

    override fun create(request: CustomerCreateRequest): ResponseEntity<CustomerResponse> {
        val books = booksRepository.findAllByIdInAndIsDeleted(request.bookIds, false)
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

        books.forEach {
            it.customer = savedCustomer
        }
        booksRepository.saveAll(books)

        return ResponseEntity(
            CustomerResponse.of(savedCustomer),
            HttpStatus.CREATED
        )
    }

    @Transactional
    override fun bringBackBooks(request: PutBooksRequest): CustomerResponse {
        val customer = customersRepository.findByIdAndIsDeleted(request.customerId, false)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")

        val books = customer.books
            .filter { it.id in request.bookIds && !it.isDeleted }
            .toMutableSet()

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
        val customer = customersRepository.findByIdAndIsDeleted(request.customerId, false)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")

        val books = booksRepository.findAllByIdInAndIsDeleted(request.bookIds, false)
            .filter { it.id in request.bookIds && it.customer == null && !it.isDeleted }
            .toMutableSet()

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
        val customer = customersRepository.findByIdAndIsDeleted(id, false)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found")

        customer.isDeleted = true
        customersRepository.save(customer)

        return ResponseEntity.ok().build()
    }
}