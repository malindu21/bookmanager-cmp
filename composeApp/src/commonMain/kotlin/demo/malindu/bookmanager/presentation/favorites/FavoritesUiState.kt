package demo.malindu.bookmanager.presentation.favorites

import demo.malindu.bookmanager.domain.model.Book

data class FavoritesUiState(
    val books: List<Book> = emptyList(),
    val isEmpty: Boolean = false
)
