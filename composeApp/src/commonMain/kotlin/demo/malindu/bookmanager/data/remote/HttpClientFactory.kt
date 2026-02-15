package demo.malindu.bookmanager.data.remote

import io.ktor.client.HttpClient

expect class HttpClientFactory() {
    fun create(): HttpClient
}
