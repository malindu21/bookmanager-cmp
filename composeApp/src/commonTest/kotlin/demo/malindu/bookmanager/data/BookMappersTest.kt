package demo.malindu.bookmanager.data

import demo.malindu.bookmanager.data.mapper.toDomain
import demo.malindu.bookmanager.data.remote.dto.BookDocDto
import demo.malindu.bookmanager.data.remote.dto.WorkDetailsDto
import kotlin.test.Test
import kotlin.test.assertEquals

class BookMappersTest {
    @Test
    fun mapBookDocToDomain() {
        val dto = BookDocDto(
            key = "/works/OL123W",
            title = "Test",
            authorName = listOf("Author"),
            firstPublishYear = 1999,
            coverId = 42
        )

        val book = dto.toDomain()

        assertEquals("/works/OL123W", book.id)
        assertEquals("Test", book.title)
        assertEquals("Author", book.author)
        assertEquals(1999L, book.firstPublishYear)
        assertEquals(42L, book.coverId)
    }

    @Test
    fun mapWorkDetailsToDomain() {
        val dto = WorkDetailsDto(
            key = "/works/OL123W",
            title = "Details",
            subjects = listOf("A", "B"),
            firstPublishYear = 2000,
            covers = listOf(10)
        )

        val details = dto.toDomain(fallbackCoverId = null)

        assertEquals("Details", details.title)
        assertEquals(2, details.subjects.size)
        assertEquals(2000L, details.firstPublishYear)
        assertEquals(10L, details.coverId)
    }
}
