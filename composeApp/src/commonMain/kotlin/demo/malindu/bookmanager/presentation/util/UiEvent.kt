package demo.malindu.bookmanager.presentation.util

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
}
