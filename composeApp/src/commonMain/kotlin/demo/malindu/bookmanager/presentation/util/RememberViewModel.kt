package demo.malindu.bookmanager.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember

@Composable
fun <T : ViewModel> rememberViewModel(factory: () -> T): T {
    val vm = remember { factory() }
    DisposableEffect(Unit) {
        onDispose { vm.onCleared() }
    }
    return vm
}
