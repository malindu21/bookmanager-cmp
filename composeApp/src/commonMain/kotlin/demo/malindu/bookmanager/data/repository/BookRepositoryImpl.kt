package demo.malindu.bookmanager.data.repository

import demo.malindu.bookmanager.data.mapper.toDomain
import demo.malindu.bookmanager.data.remote.OpenLibraryApi
import demo.malindu.bookmanager.domain.model.Book
import demo.malindu.bookmanager.domain.model.BookDetails
import demo.malindu.bookmanager.domain.repository.BookRepository
import demo.malindu.bookmanager.presentation.util.Result
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.utils.io.errors.IOException

class BookRepositoryImpl(private val api: OpenLibraryApi) : BookRepository {
    override suspend fun searchBooks(query: String, page: Int): Result<List<Book>> {
        return try {
            val response = api.searchBooks(query, page)
            Result.Success(response.docs.map { it.toDomain() })
        } catch (e: ClientRequestException) {
            Result.Error("Request error: ${e.response.status.value}", e)
        } catch (e: ServerResponseException) {
            Result.Error("Server error: ${e.response.status.value}", e)
        } catch (e: IOException) {
            Result.Error("Network error", e)
        } catch (e: Exception) {
            Result.Error("Unexpected error", e)
        }
    }

    override suspend fun getBookDetails(workKey: String): Result<BookDetails> {
        return try {
            val dto = api.getWorkDetails(workKey)
            Result.Success(dto.toDomain(fallbackCoverId = null))
        } catch (e: ClientRequestException) {
            Result.Error("Request error: ${e.response.status.value}", e)
        } catch (e: ServerResponseException) {
            Result.Error("Server error: ${e.response.status.value}", e)
        } catch (e: IOException) {
            Result.Error("Network error", e)
        } catch (e: Exception) {
            Result.Error("Unexpected error", e)
        }
    }
}
