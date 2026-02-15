package demo.malindu.bookmanager.data.repository

import demo.malindu.bookmanager.core.currentTimeMillis
import demo.malindu.bookmanager.data.local.FavoritesLocalDataSource
import demo.malindu.bookmanager.data.mapper.toDomain
import demo.malindu.bookmanager.domain.model.Book
import demo.malindu.bookmanager.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesRepositoryImpl(
    private val localDataSource: FavoritesLocalDataSource
) : FavoritesRepository {
    override fun observeFavorites(): Flow<List<Book>> {
        return localDataSource.observeAll().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun addFavorite(book: Book) {
        localDataSource.upsert(
            id = book.id,
            workKey = book.workKey,
            title = book.title,
            author = book.author,
            year = book.firstPublishYear,
            coverId = book.coverId,
            savedAt = currentTimeMillis()
        )
    }

    override suspend fun removeFavorite(bookId: String) {
        localDataSource.deleteById(bookId)
    }

    override fun isFavorite(bookId: String): Flow<Boolean> {
        return localDataSource.observeById(bookId).map { it != null }
    }
}
