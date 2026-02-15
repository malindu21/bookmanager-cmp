package demo.malindu.bookmanager.domain

import demo.malindu.bookmanager.domain.model.Book
import demo.malindu.bookmanager.domain.model.BookDetails
import demo.malindu.bookmanager.domain.repository.BookRepository
import demo.malindu.bookmanager.domain.repository.FavoritesRepository
import demo.malindu.bookmanager.domain.usecase.AddFavorite
import demo.malindu.bookmanager.domain.usecase.GetBookDetails
import demo.malindu.bookmanager.domain.usecase.IsFavorite
import demo.malindu.bookmanager.domain.usecase.ObserveFavorites
import demo.malindu.bookmanager.domain.usecase.RemoveFavorite
import demo.malindu.bookmanager.domain.usecase.SearchBooks
import demo.malindu.bookmanager.presentation.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UseCasesTest {
    private val fakeBookRepo = object : BookRepository {
        override suspend fun searchBooks(query: String, page: Int): Result<List<Book>> {
            return Result.Success(listOf(Book("1", "/works/1", "Title", "Author", 2000L, 1L)))
        }

        override suspend fun getBookDetails(workKey: String): Result<BookDetails> {
            return Result.Success(BookDetails(workKey, "Title", "Desc", emptyList(), 2000L, emptyList(), 1L))
        }
    }

    private val favoritesFlow = MutableStateFlow<List<Book>>(emptyList())
    private val fakeFavRepo = object : FavoritesRepository {
        override fun observeFavorites(): Flow<List<Book>> = favoritesFlow
        override suspend fun addFavorite(book: Book) {
            favoritesFlow.value = favoritesFlow.value + book
        }
        override suspend fun removeFavorite(bookId: String) {
            favoritesFlow.value = favoritesFlow.value.filterNot { it.id == bookId }
        }
        override fun isFavorite(bookId: String): Flow<Boolean> {
            return MutableStateFlow(favoritesFlow.value.any { it.id == bookId })
        }
    }

    @Test
    fun searchBooks_returnsData() = runTest {
        val useCase = SearchBooks(fakeBookRepo)
        val result = useCase("kotlin", 1)
        assertTrue(result is Result.Success)
    }

    @Test
    fun favorites_flow_updates() = runTest {
        val add = AddFavorite(fakeFavRepo)
        val observe = ObserveFavorites(fakeFavRepo)
        val book = Book("1", "/works/1", "Title", "Author", 2000L, 1L)

        add(book)
        val list = observe().first()
        assertEquals(1, list.size)
    }
}
