package demo.malindu.bookmanager.domain.model

data class Book(
    val id: String,
    val workKey: String,
    val title: String,
    val author: String,
    val firstPublishYear: Long?,
    val coverId: Long?
) {
    val coverUrl: String?
        get() = coverId?.let { "https://covers.openlibrary.org/b/id/${it}-L.jpg" }
}
