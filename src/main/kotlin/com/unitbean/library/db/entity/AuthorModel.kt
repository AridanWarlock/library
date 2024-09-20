package com.unitbean.library.db.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import org.springframework.data.repository.CrudRepository
import java.util.*


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
    val books: MutableSet<BookModel> = mutableSetOf(),

    ) : BaseEntity()

interface AuthorRepository : CrudRepository<AuthorModel, UUID> {
    fun findAllByIsDeleted(isDeleted: Boolean): List<AuthorModel>
    fun findByIdAndIsDeleted(id: UUID, isDeleted: Boolean): AuthorModel?
    fun findAllByIdInAndIsDeleted(ids: List<UUID>, isDeleted: Boolean): List<AuthorModel>
}
