package demo.malindu.bookmanager.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class WorkDetailsDto(
    @SerialName("key") val key: String = "",
    @SerialName("title") val title: String = "",
    @SerialName("description") val description: JsonElement? = null,
    @SerialName("subjects") val subjects: List<String> = emptyList(),
    @SerialName("first_publish_year") val firstPublishYear: Int? = null,
    @SerialName("first_publish_date") val firstPublishDate: String? = null,
    @SerialName("covers") val covers: List<Int> = emptyList()
)
