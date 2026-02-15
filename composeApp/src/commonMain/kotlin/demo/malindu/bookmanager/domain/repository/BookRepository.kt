package demo.malindu.bookmanager.domain.repository

import demo.malindu.bookmanager.domain.model.Book
import demo.malindu.bookmanager.domain.model.BookDetails
import demo.malindu.bookmanager.presentation.util.Result

interface BookRepository {
    suspend fun searchBooks(query: String, page: Int): Result<List<Book>>
    suspend fun getBookDetails(workKey: String): Result<BookDetails>
}
