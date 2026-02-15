package demo.malindu.bookmanager.domain.usecase

import demo.malindu.bookmanager.domain.model.BookDetails
import demo.malindu.bookmanager.domain.repository.BookRepository
import demo.malindu.bookmanager.presentation.util.Result

class GetBookDetails(private val repository: BookRepository) {
    suspend operator fun invoke(workKey: String): Result<BookDetails> {
        return repository.getBookDetails(workKey)
    }
}
