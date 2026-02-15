package demo.malindu.bookmanager.data.mapper

import demo.malindu.bookmanager.data.local.Favorites
import demo.malindu.bookmanager.data.remote.dto.BookDocDto
import demo.malindu.bookmanager.data.remote.dto.WorkDetailsDto
import demo.malindu.bookmanager.domain.model.Book
import demo.malindu.bookmanager.domain.model.BookDetails
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive

fun BookDocDto.toDomain(): Book {
    return Book(
        id = key,
        workKey = key,
        title = title,
        author = authorName.firstOrNull().orEmpty(),
        firstPublishYear = firstPublishYear?.toLong(),
        coverId = coverId?.toLong()
    )
}

fun Favorites.toDomain(): Book {
    return Book(
        id = id,
        workKey = workKey,
        title = title,
        author = author,
        firstPublishYear = year,
        coverId = coverId
    )
}

fun Book.toEntity(savedAt: Long): Favorites {
    return Favorites(
        id = id,
        workKey = workKey,
        title = title,
        author = author,
        year = firstPublishYear,
        coverId = coverId,
        savedAt = savedAt
    )
}

fun WorkDetailsDto.toDomain(fallbackCoverId: Long?): BookDetails {
    val descriptionText = when (val d = description) {
        is JsonPrimitive -> d.content
        is JsonObject -> (d["value"] as? JsonPrimitive)?.content
        else -> null
    }

    val yearFromDate = firstPublishDate?.take(4)?.toLongOrNull()
    val cover = covers.firstOrNull()?.toLong() ?: fallbackCoverId

    return BookDetails(
        workKey = key,
        title = title,
        description = descriptionText,
        subjects = subjects,
        firstPublishYear = firstPublishYear?.toLong() ?: yearFromDate,
        authors = emptyList(),
        coverId = cover
    )
}
