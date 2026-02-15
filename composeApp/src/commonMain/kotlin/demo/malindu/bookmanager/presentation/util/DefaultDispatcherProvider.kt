package demo.malindu.bookmanager.presentation.util

import kotlinx.coroutines.Dispatchers

object DefaultDispatcherProvider : DispatcherProvider {
    override val io = Dispatchers.Default
    override val main = Dispatchers.Main
    override val default = Dispatchers.Default
}
