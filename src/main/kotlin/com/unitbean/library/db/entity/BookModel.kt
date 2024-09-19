package com.unitbean.library.db.entity

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param


@Entity
@Table(name = "books")
class BookModel(
    @Column(name = "title", nullable = false)
    val title: String,

    @Column(name = "description", nullable = false)
    val description: String,

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "books_authors_mapping",
        joinColumns = [JoinColumn(name = "book_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "author_id", referencedColumnName = "id")]
    )
    val authors: MutableSet<AuthorModel> = mutableSetOf(),

    @Column(name = "year_of_release", nullable = false)
    val yearOfRelease: Int,

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    val customer: CustomerModel? = null,

    ) : BaseEntity()

interface BooksRepository : JpaRepository<BookModel, UUID> {
    fun findAllByIdIn(ids: List<UUID>): List<BookModel>

    fun findAllByIsDeletedIsFalse(): List<BookModel>

    @Query(
        value = """
        select book from books as book
        inner join books_authors_mapping as ba_map on ba_map.book_id = book.id
        inner join authors as author on author.id = ba_map.author_id
        where !book.is_deleted and !author.is_deleted and
        book.year_of_release > :year_of_release
        group by book
        having count(author) > :authors_count
    """, nativeQuery = true
    )
    fun findAllByTask1NativeQuery(
        @Param("year_of_release") yearOfRelease: Int,
        @Param("authors_count") authorsCount: Int
    ): List<BookModel>


    @Query("""
        select book from BookModel as book
        inner join book.authors as author
        where book.isDeleted = false and author.isDeleted = false and
        book.yearOfRelease > :yearOfRelease
        group by author
        having count(author) > :authorsCount
    """)
    fun findAllByTask1(
        @Param("yearOfRelease") yearOfRelease: Int,
        @Param("authorsCount") authorsCount: Int
    ): List<BookModel>
}
