package demo.malindu.bookmanager.di

import demo.malindu.bookmanager.data.local.DatabaseDriverFactory
import demo.malindu.bookmanager.data.remote.HttpClientFactory

actual fun createAppContainer(): AppContainer {
    return AppContainer(
        databaseDriverFactory = DatabaseDriverFactory(),
        httpClientFactory = HttpClientFactory()
    )
}
