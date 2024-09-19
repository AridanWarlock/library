package com.unitbean.library.interfaces

import com.unitbean.library.models.requests.CustomersTask2Request
import com.unitbean.library.models.requests.CustomersTask3Request
import com.unitbean.library.models.responses.CustomerResponse

interface ICustomerService {
    fun getAll(): List<CustomerResponse>

    fun getAllByTask2(request: CustomersTask2Request): List<CustomerResponse>
    fun getAllByTask2NativeQuery(request: CustomersTask2Request): List<CustomerResponse>

    fun getAllByTask3(request: CustomersTask3Request): List<CustomerResponse>
    fun getAllByTask3NativeQuery(request: CustomersTask3Request): List<CustomerResponse>
}