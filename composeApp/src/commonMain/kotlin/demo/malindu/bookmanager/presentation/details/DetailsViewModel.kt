package demo.malindu.bookmanager.presentation.details

import demo.malindu.bookmanager.domain.model.Book
import demo.malindu.bookmanager.domain.model.BookDetails
import demo.malindu.bookmanager.domain.usecase.AddFavorite
import demo.malindu.bookmanager.domain.usecase.GetBookDetails
import demo.malindu.bookmanager.domain.usecase.IsFavorite
import demo.malindu.bookmanager.domain.usecase.RemoveFavorite
import demo.malindu.bookmanager.presentation.util.DispatcherProvider
import demo.malindu.bookmanager.presentation.util.Result
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

class DetailsViewModel(
    private val bookStub: Book,
    private val getBookDetails: GetBookDetails,
    private val addFavorite: AddFavorite,
    private val removeFavorite: RemoveFavorite,
    private val isFavorite: IsFavorite,
    dispatcherProvider: DispatcherProvider
) : ViewModel(dispatcherProvider) {

    private val _state = MutableStateFlow(DetailsUiState())
    val state: StateFlow<DetailsUiState> = _state.asStateFlow()

    private val eventsChannel = Channel<UiEvent>(Channel.BUFFERED)
    val events = eventsChannel.receiveAsFlow()

    init {
        observeFavorite()
        loadDetails()
    }

    private fun observeFavorite() {
        viewModelScope.launch {
            isFavorite(bookStub.id).collectLatest { fav ->
                _state.update { it.copy(isFavorite = fav) }
            }
        }
    }

    private fun loadDetails() {
        viewModelScope.launch {
            when (val result = getBookDetails(bookStub.workKey)) {
                is Result.Success -> {
                    val merged = mergeWithStub(result.data, bookStub)
                    _state.update { it.copy(isLoading = false, details = merged, error = null) }
                }
                is Result.Error -> {
                    _state.update { it.copy(isLoading = false, error = result.message) }
                    eventsChannel.send(UiEvent.ShowSnackbar(result.message))
                }
            }
        }
    }

    fun retry() {
        _state.update { it.copy(isLoading = true, error = null) }
        loadDetails()
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val fav = state.value.isFavorite
            if (fav) {
                removeFavorite(bookStub.id)
                eventsChannel.send(UiEvent.ShowSnackbar("Removed from favorites"))
            } else {
                addFavorite(bookStub)
                eventsChannel.send(UiEvent.ShowSnackbar("Added to favorites"))
            }
        }
    }

    private fun mergeWithStub(details: BookDetails, stub: Book): BookDetails {
        val authors = if (details.authors.isEmpty()) listOf(stub.author).filter { it.isNotBlank() } else details.authors
        val coverId = details.coverId ?: stub.coverId
        return details.copy(authors = authors, coverId = coverId)
    }
}
