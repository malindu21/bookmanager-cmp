package demo.malindu.bookmanager.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import demo.malindu.bookmanager.data.local.Favorites
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesLocalDataSource(private val database: AppDatabase) {
    private val queries = database.favoritesQueries

    fun observeAll(): Flow<List<Favorites>> {
        return queries.selectAll().asFlow().mapToList(Dispatchers.Default)
    }

    fun observeById(id: String): Flow<Favorites?> {
        return queries.selectById(id).asFlow().mapToOneOrNull(Dispatchers.Default)
    }

    fun upsert(
        id: String,
        workKey: String,
        title: String,
        author: String,
        year: Long?,
        coverId: Long?,
        savedAt: Long
    ) {
        queries.upsert(id, workKey, title, author, year, coverId, savedAt)
    }

    fun deleteById(id: String) {
        queries.deleteById(id)
    }
}
