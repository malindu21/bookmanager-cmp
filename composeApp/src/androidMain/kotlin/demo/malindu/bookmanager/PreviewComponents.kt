package demo.malindu.bookmanager

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import demo.malindu.bookmanager.domain.model.Book
import demo.malindu.bookmanager.presentation.components.BookCard
import demo.malindu.bookmanager.presentation.theme.AppTheme

@Preview
@Composable
fun BookCardPreview() {
    AppTheme(darkTheme = false) {
        BookCard(
            book = Book("1", "/works/1", "Sample Book", "Author", 2020L, null),
            isFavorite = true,
            onClick = {},
            onToggleFavorite = {}
        )
    }
}
