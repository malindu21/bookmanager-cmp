package demo.malindu.bookmanager.domain.usecase

import demo.malindu.bookmanager.domain.model.Book
import demo.malindu.bookmanager.domain.repository.BookRepository
import demo.malindu.bookmanager.presentation.util.Result

class SearchBooks(private val repository: BookRepository) {
    suspend operator fun invoke(query: String, page: Int): Result<List<Book>> {
        return repository.searchBooks(query, page)
    }
}
