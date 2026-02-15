package demo.malindu.bookmanager.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
    @SerialName("docs") val docs: List<BookDocDto> = emptyList(),
    @SerialName("numFound") val numFound: Int? = null
)

@Serializable
data class BookDocDto(
    @SerialName("key") val key: String = "",
    @SerialName("title") val title: String = "",
    @SerialName("author_name") val authorName: List<String> = emptyList(),
    @SerialName("first_publish_year") val firstPublishYear: Int? = null,
    @SerialName("cover_i") val coverId: Int? = null
)
