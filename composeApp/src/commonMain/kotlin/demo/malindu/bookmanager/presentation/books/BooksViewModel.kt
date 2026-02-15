package demo.malindu.bookmanager.presentation.books

import demo.malindu.bookmanager.domain.model.Book
import demo.malindu.bookmanager.domain.usecase.AddFavorite
import demo.malindu.bookmanager.domain.usecase.ObserveFavorites
import demo.malindu.bookmanager.domain.usecase.RemoveFavorite
import demo.malindu.bookmanager.domain.usecase.SearchBooks
import demo.malindu.bookmanager.presentation.util.DispatcherProvider
import demo.malindu.bookmanager.presentation.util.Result
import demo.malindu.bookmanager.presentation.util.UiEvent
import demo.malindu.bookmanager.presentation.util.ViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BooksViewModel(
    private val searchBooks: SearchBooks,
    private val observeFavorites: ObserveFavorites,
    private val addFavorite: AddFavorite,
    private val removeFavorite: RemoveFavorite,
    dispatcherProvider: DispatcherProvider
) : ViewModel(dispatcherProvider) {

    private val _state = MutableStateFlow(BooksUiState())
    val state: StateFlow<BooksUiState> = _state.asStateFlow()

    private val eventsChannel = Channel<UiEvent>(Channel.BUFFERED)
    val events = eventsChannel.receiveAsFlow()

    private var currentPage = 1
    private var currentQuery = ""

    val favoritesFlow: StateFlow<Set<String>> = observeFavorites()
        .map { list -> list.map { it.id }.toSet() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptySet())

    init {
        viewModelScope.launch {
            favoritesFlow.collect { favs ->
                _state.update { it.copy(favorites = favs) }
            }
        }
        observeSearchQuery()
    }

    fun onQueryChanged(query: String) {
        _state.update { it.copy(query = query) }
    }

    fun onRetry() {
        performSearch(reset = true)
    }

    fun loadNextPage() {
        val s = state.value
        if (s.isLoading || s.isAppending || s.endReached || s.query.isBlank()) return
        performSearch(reset = false)
    }

    fun toggleFavorite(book: Book) {
        viewModelScope.launch {
            val isFav = state.value.favorites.contains(book.id)
            if (isFav) {
                removeFavorite(book.id)
                eventsChannel.send(UiEvent.ShowSnackbar("Removed from favorites"))
            } else {
                addFavorite(book)
                eventsChannel.send(UiEvent.ShowSnackbar("Added to favorites"))
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch {
            _state.map { it.query }
                .debounce(300)
                .collectLatest { query ->
                    currentQuery = query
                    performSearch(reset = true)
                }
        }
    }

    private fun performSearch(reset: Boolean) {
        if (currentQuery.isBlank()) {
            _state.update {
                it.copy(books = emptyList(), error = null, isLoading = false, endReached = false)
            }
            return
        }

        if (reset) {
            currentPage = 1
            _state.update { it.copy(isLoading = true, error = null, endReached = false) }
        } else {
            _state.update { it.copy(isAppending = true, error = null) }
        }

        viewModelScope.launch {
            when (val result = searchBooks(currentQuery, currentPage)) {
                is Result.Success -> {
                    val newItems = result.data
                    _state.update { state ->
                        val combined = if (reset) newItems else state.books + newItems
                        state.copy(
                            books = combined,
                            isLoading = false,
                            isAppending = false,
                            endReached = newItems.isEmpty(),
                            error = null
                        )
                    }
                    if (newItems.isNotEmpty()) currentPage++
                }
                is Result.Error -> {
                    _state.update { it.copy(isLoading = false, isAppending = false, error = result.message) }
                    eventsChannel.send(UiEvent.ShowSnackbar(result.message))
                }
            }
        }
    }
}
