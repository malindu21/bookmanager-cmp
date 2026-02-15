package demo.malindu.bookmanager.domain.usecase

import demo.malindu.bookmanager.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow

class IsFavorite(private val repository: FavoritesRepository) {
    operator fun invoke(bookId: String): Flow<Boolean> = repository.isFavorite(bookId)
}
