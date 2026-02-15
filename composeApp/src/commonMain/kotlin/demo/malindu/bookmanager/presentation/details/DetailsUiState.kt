package demo.malindu.bookmanager.presentation.details

import demo.malindu.bookmanager.domain.model.BookDetails

data class DetailsUiState(
    val isLoading: Boolean = true,
    val details: BookDetails? = null,
    val error: String? = null,
    val isFavorite: Boolean = false
)
