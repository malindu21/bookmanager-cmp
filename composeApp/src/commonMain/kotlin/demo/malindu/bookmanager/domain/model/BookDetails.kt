package demo.malindu.bookmanager.domain.model

data class BookDetails(
    val workKey: String,
    val title: String,
    val description: String?,
    val subjects: List<String>,
    val firstPublishYear: Long?,
    val authors: List<String>,
    val coverId: Long?
) {
    val coverUrl: String?
        get() = coverId?.let { "https://covers.openlibrary.org/b/id/${it}-L.jpg" }
}
