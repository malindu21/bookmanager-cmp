package demo.malindu.bookmanager.domain.usecase

import demo.malindu.bookmanager.domain.model.Book
import demo.malindu.bookmanager.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow

class ObserveFavorites(private val repository: FavoritesRepository) {
    operator fun invoke(): Flow<List<Book>> = repository.observeFavorites()
}
