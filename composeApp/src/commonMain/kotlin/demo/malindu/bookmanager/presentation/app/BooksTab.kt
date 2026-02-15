package demo.malindu.bookmanager.presentation.app

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import demo.malindu.bookmanager.presentation.books.BooksRoute

class BooksTab(private val snackbarHostState: SnackbarHostState) : Tab {
    override val options: TabOptions
        @Composable get() = TabOptions(
            index = 0u,
            title = "Books",
            icon = null
        )

    @Composable
    override fun Content() {
        BooksRoute(snackbarHostState)
    }
}
