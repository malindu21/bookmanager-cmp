package demo.malindu.bookmanager.data.remote

import demo.malindu.bookmanager.data.remote.dto.SearchResponseDto
import demo.malindu.bookmanager.data.remote.dto.WorkDetailsDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class OpenLibraryApi(private val client: HttpClient) {
    suspend fun searchBooks(query: String, page: Int): SearchResponseDto {
        return client.get("https://openlibrary.org/search.json") {
            parameter("q", query)
            parameter("page", page)
        }.body()
    }

    suspend fun getWorkDetails(workKey: String): WorkDetailsDto {
        return client.get("https://openlibrary.org${workKey}.json").body()
    }
}
