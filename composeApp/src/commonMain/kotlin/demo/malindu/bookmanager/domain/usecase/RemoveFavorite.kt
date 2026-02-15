package demo.malindu.bookmanager.domain.usecase

import demo.malindu.bookmanager.domain.repository.FavoritesRepository

class RemoveFavorite(private val repository: FavoritesRepository) {
    suspend operator fun invoke(bookId: String) = repository.removeFavorite(bookId)
}
