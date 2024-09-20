package com.unitbean.library.db.entity

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

@Entity
@Table(name = "customers")
class CustomerModel(

    @Column(name = "first_name", nullable = false)
    val firstName: String,

    @Column(name = "second_name", nullable = false)
    val secondName: String,

    @Column(name = "phone", nullable = false)
    val phone: String,

    @OneToMany(
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY,
        mappedBy = "customer"
    )
    val books: MutableSet<BookModel> = mutableSetOf(),

    ) : BaseEntity()

interface CustomersRepository : JpaRepository<CustomerModel, UUID> {
    fun findByIdAndIsDeleted(id: UUID, isDeleted: Boolean): CustomerModel?
    fun findAllByIsDeleted(isDeleted: Boolean): List<CustomerModel>
    fun findAllByIdInAndIsDeleted(ids: List<UUID>, isDeleted: Boolean): List<CustomerModel>

    @Query(
        value = """
            select distinct customer.* from customers customer
            inner join books book on customer.id = book.customer_id
            inner join books_authors_mapping bam on book.id = bam.book_id
            inner join authors author on author.id = bam.author_id
            where customer.is_deleted = false and book.is_deleted = false and author.is_deleted = false and
            author.year_of_birth > :yearOfBirth
        """,
        nativeQuery = true,
    )
    fun findAllTask2Native(@Param("yearOfBirth") yearOfBirth: Int): List<CustomerModel>

    @Query(
        """
        select customer from CustomerModel customer
        inner join customer.books as book
        inner join book.authors as author
        where customer.isDeleted = false and book.isDeleted = false and author.isDeleted = false
        and author.yearOfBirth > :yearOfBirth
    """
    )
    fun findAllTask2(@Param("yearOfBirth") yearOfBirth: Int): List<CustomerModel>

    @Query(
        value = """
            select customer.* from customers customer
            inner join books book on customer.id = book.customer_id
            inner join books_authors_mapping bam on book.id = bam.book_id
            inner join authors author on author.id = bam.author_id
            where customer.is_deleted = false and book.is_deleted = false and author.is_deleted = false and
            author.first_name = :firstName
        """,
        nativeQuery = true,
    )
    fun findAllTask3Native(@Param("firstName") firstName: String): List<CustomerModel>

    @Query(
        """
        select customer from CustomerModel customer
        inner join customer.books as book
        inner join book.authors as author
        where customer.isDeleted = false and book.isDeleted = false and author.isDeleted = false
        and author.firstName = :firstName
    """
    )
    fun findAllByTask3(@Param("firstName") firstName: String): List<CustomerModel>
}

