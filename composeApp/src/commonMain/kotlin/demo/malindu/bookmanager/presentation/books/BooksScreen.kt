package demo.malindu.bookmanager.presentation.books

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import demo.malindu.bookmanager.domain.model.Book
import demo.malindu.bookmanager.presentation.components.BookCard
import demo.malindu.bookmanager.presentation.components.EmptyState
import demo.malindu.bookmanager.presentation.components.ErrorState
import demo.malindu.bookmanager.presentation.components.SearchBar
import demo.malindu.bookmanager.presentation.components.ShimmerBox
import kotlinx.coroutines.flow.StateFlow

@Composable
fun BooksScreen(
    state: StateFlow<BooksUiState>,
    onQueryChange: (String) -> Unit,
    onRetry: () -> Unit,
    onLoadMore: () -> Unit,
    onToggleFavorite: (Book) -> Unit,
    onBookClick: (Book) -> Unit
) {
    val uiState by state.collectAsState()
    val listState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Discover Books",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "Search OpenLibrary and save favorites",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(
            query = uiState.query,
            onQueryChange = onQueryChange,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))

        when {
            uiState.isLoading && uiState.books.isEmpty() -> {
                ShimmerList()
            }
            uiState.error != null && uiState.books.isEmpty() -> {
                ErrorState(message = uiState.error ?: "Unknown error", onRetry = onRetry)
            }
            uiState.books.isEmpty() && uiState.query.isNotBlank() -> {
                EmptyState(
                    title = "No results",
                    subtitle = "Try a different search"
                )
            }
            else -> {
                BooksList(
                    books = uiState.books,
                    favorites = uiState.favorites,
                    listState = listState,
                    isAppending = uiState.isAppending,
                    onBookClick = onBookClick,
                    onToggleFavorite = onToggleFavorite
                )
            }
        }
    }

    InfiniteListHandler(listState) {
        onLoadMore()
    }
}

@Composable
private fun BooksList(
    books: List<Book>,
    favorites: Set<String>,
    listState: LazyListState,
    isAppending: Boolean,
    onBookClick: (Book) -> Unit,
    onToggleFavorite: (Book) -> Unit
) {
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        items(books, key = { it.id }) { book ->
            BookCard(
                book = book,
                isFavorite = favorites.contains(book.id),
                onClick = { onBookClick(book) },
                onToggleFavorite = { onToggleFavorite(book) }
            )
        }
        if (isAppending) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
private fun ShimmerList() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        repeat(6) {
            ShimmerBox(modifier = Modifier.fillMaxWidth().height(120.dp))
        }
    }
}

@Composable
private fun InfiniteListHandler(
    listState: LazyListState,
    buffer: Int = 4,
    onLoadMore: () -> Unit
) {
    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val total = listState.layoutInfo.totalItemsCount
            lastVisible >= total - buffer && total > 0
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) onLoadMore()
    }
}
