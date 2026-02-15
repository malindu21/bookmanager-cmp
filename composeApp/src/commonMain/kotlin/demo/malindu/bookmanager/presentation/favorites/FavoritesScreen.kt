package demo.malindu.bookmanager.presentation.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import demo.malindu.bookmanager.domain.model.Book
import demo.malindu.bookmanager.presentation.components.BookCard
import demo.malindu.bookmanager.presentation.components.EmptyState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun FavoritesScreen(
    state: StateFlow<FavoritesUiState>,
    onRemove: (String) -> Unit,
    onBookClick: (Book) -> Unit
) {
    val uiState by state.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Favorites", style = MaterialTheme.typography.titleLarge)
        if (uiState.isEmpty) {
            EmptyState(title = "No favorites", subtitle = "Save books for offline access")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                items(uiState.books, key = { it.id }) { book ->
                    BookCard(
                        book = book,
                        isFavorite = true,
                        onClick = { onBookClick(book) },
                        onToggleFavorite = { onRemove(book.id) }
                    )
                }
            }
        }
    }
}
