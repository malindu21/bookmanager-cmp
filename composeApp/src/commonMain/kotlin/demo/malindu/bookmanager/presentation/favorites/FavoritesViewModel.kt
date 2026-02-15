package demo.malindu.bookmanager.presentation.favorites

import demo.malindu.bookmanager.domain.usecase.ObserveFavorites
import demo.malindu.bookmanager.domain.usecase.RemoveFavorite
import demo.malindu.bookmanager.presentation.util.DispatcherProvider
import demo.malindu.bookmanager.presentation.util.UiEvent
import demo.malindu.bookmanager.presentation.util.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val observeFavorites: ObserveFavorites,
    private val removeFavorite: RemoveFavorite,
    dispatcherProvider: DispatcherProvider
) : ViewModel(dispatcherProvider) {

    private val _state = MutableStateFlow(FavoritesUiState())
    val state: StateFlow<FavoritesUiState> = _state.asStateFlow()

    private val eventsChannel = Channel<UiEvent>(Channel.BUFFERED)
    val events = eventsChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            observeFavorites().collectLatest { list ->
                _state.update { it.copy(books = list, isEmpty = list.isEmpty()) }
            }
        }
    }

    fun remove(bookId: String) {
        viewModelScope.launch {
            removeFavorite(bookId)
            eventsChannel.send(UiEvent.ShowSnackbar("Removed from favorites"))
        }
    }
}
