package demo.malindu.bookmanager.domain.repository

import demo.malindu.bookmanager.domain.model.Book
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun observeFavorites(): Flow<List<Book>>
    suspend fun addFavorite(book: Book)
    suspend fun removeFavorite(bookId: String)
    fun isFavorite(bookId: String): Flow<Boolean>
}
