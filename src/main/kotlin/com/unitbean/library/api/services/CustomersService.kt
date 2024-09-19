package com.unitbean.library.api.services

import com.unitbean.library.db.entity.CustomersRepository
import com.unitbean.library.interfaces.ICustomerService
import com.unitbean.library.models.requests.CustomersTask2Request
import com.unitbean.library.models.requests.CustomersTask3Request
import com.unitbean.library.models.responses.CustomerResponse
import org.springframework.stereotype.Service

@Service
class CustomersService(
    val customersRepository: CustomersRepository
) : ICustomerService {
    override fun getAll(): List<CustomerResponse> {
        return customersRepository.findAllByDeletedIsFalse().map { CustomerResponse.of(it) }
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
}