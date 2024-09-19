package com.unitbean.library.db.entity

import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository


@Entity
@Table(name = "authors")
class AuthorModel(

    @Column(name = "first_name", nullable = false)
    val firstName: String,

    @Column(name = "second_name", nullable = false)
    val secondName: String,

    @Column(name = "year_of_birth", nullable = false)
    val yearOfBirth: Int,

    @ManyToMany(mappedBy = "authors")
    val books: List<BookModel> = listOf(),

) : BaseEntity()

interface AuthorRepository : CrudRepository<AuthorModel, Long> {
    fun findAllByDeletedFalse(): List<AuthorModel>
}
