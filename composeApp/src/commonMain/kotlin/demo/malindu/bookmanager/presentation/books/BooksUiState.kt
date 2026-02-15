package demo.malindu.bookmanager.presentation.books

import demo.malindu.bookmanager.domain.model.Book

data class BooksUiState(
    val query: String = "",
    val books: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val isAppending: Boolean = false,
    val error: String? = null,
    val endReached: Boolean = false,
    val favorites: Set<String> = emptySet()
)
