package demo.malindu.bookmanager.presentation.books

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.navigator.LocalNavigator
import demo.malindu.bookmanager.LocalAppContainer
import demo.malindu.bookmanager.presentation.details.DetailsScreen
import demo.malindu.bookmanager.presentation.util.UiEvent
import demo.malindu.bookmanager.presentation.util.rememberViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun BooksRoute(snackbarHostState: SnackbarHostState) {
    val container = LocalAppContainer.current
    val navigator = LocalNavigator.current
    val rootNavigator = navigator?.parent ?: navigator

    val viewModel = rememberViewModel {
        BooksViewModel(
            searchBooks = container.searchBooks,
            observeFavorites = container.observeFavorites,
            addFavorite = container.addFavorite,
            removeFavorite = container.removeFavorite,
            dispatcherProvider = container.dispatcherProvider
        )
    }

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            if (event is UiEvent.ShowSnackbar) {
                snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    BooksScreen(
        state = viewModel.state,
        onQueryChange = viewModel::onQueryChanged,
        onRetry = viewModel::onRetry,
        onLoadMore = viewModel::loadNextPage,
        onToggleFavorite = viewModel::toggleFavorite,
        onBookClick = { book ->
            rootNavigator?.push(DetailsScreen(book))
        }
    )
}
