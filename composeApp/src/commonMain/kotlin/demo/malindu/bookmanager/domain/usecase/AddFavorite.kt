package demo.malindu.bookmanager.domain.usecase

import demo.malindu.bookmanager.domain.model.Book
import demo.malindu.bookmanager.domain.repository.FavoritesRepository

class AddFavorite(private val repository: FavoritesRepository) {
    suspend operator fun invoke(book: Book) = repository.addFavorite(book)
}
