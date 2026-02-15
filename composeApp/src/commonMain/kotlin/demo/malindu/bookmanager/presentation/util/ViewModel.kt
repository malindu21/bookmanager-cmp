package demo.malindu.bookmanager.presentation.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

open class ViewModel(dispatcherProvider: DispatcherProvider) {
    protected val viewModelScope = CoroutineScope(dispatcherProvider.main + SupervisorJob())

    open fun onCleared() {
        viewModelScope.cancel()
    }
}
