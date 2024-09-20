package com.unitbean.library.db.entity

import jakarta.annotation.Nonnull
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
    var customer: CustomerModel? = null,

    ) : BaseEntity()

interface BooksRepository : JpaRepository<BookModel, UUID> {
    fun findAllByIsDeleted(isDeleted: Boolean): List<BookModel>
    fun findAllByIdInAndIsDeleted(ids: List<UUID>, isDeleted: Boolean): List<BookModel>
    fun findByIdAndIsDeleted(id: UUID, isDeleted: Boolean): BookModel?

    @Query(
        value = """
        select book.* from (
	        select keyBook.id, count(author.id)
	        from books as keyBook
	        inner join books_authors_mapping as ba_map on ba_map.book_id = keyBook.id
	        inner join authors as author on author.id = ba_map.author_id
	        where keyBook.is_deleted = false and author.is_deleted = false
            and keyBook.year_of_release > :yearOfRelease

	        group by (keyBook.id)
	        having count(author.id) > :authorsCount
	    ) as bookIdToCount
        inner join books as book on book.id = bookIdToCount.id
        """, nativeQuery = true
    )
    fun findAllByTask1NativeQuery(
        yearOfRelease: Int,
        authorsCount: Int
    ): List<BookModel>


    @Query(
        """
        select book from BookModel as book
        inner join book.authors as author
        where book.isDeleted = false and author.isDeleted = false and
        book.yearOfRelease > :yearOfRelease
        group by book
        having count(author) > :authorsCount
    """
    )
    fun findAllByTask1(
        yearOfRelease: Int,
        authorsCount: Int
    ): List<BookModel>
}
