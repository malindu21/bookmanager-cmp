package demo.malindu.bookmanager.di

import demo.malindu.bookmanager.data.local.AppDatabase
import demo.malindu.bookmanager.data.local.DatabaseDriverFactory
import demo.malindu.bookmanager.data.local.FavoritesLocalDataSource
import demo.malindu.bookmanager.data.remote.HttpClientFactory
import demo.malindu.bookmanager.data.remote.OpenLibraryApi
import demo.malindu.bookmanager.data.repository.BookRepositoryImpl
import demo.malindu.bookmanager.data.repository.FavoritesRepositoryImpl
import demo.malindu.bookmanager.domain.repository.BookRepository
import demo.malindu.bookmanager.domain.repository.FavoritesRepository
import demo.malindu.bookmanager.domain.usecase.AddFavorite
import demo.malindu.bookmanager.domain.usecase.GetBookDetails
import demo.malindu.bookmanager.domain.usecase.IsFavorite
import demo.malindu.bookmanager.domain.usecase.ObserveFavorites
import demo.malindu.bookmanager.domain.usecase.RemoveFavorite
import demo.malindu.bookmanager.domain.usecase.SearchBooks
import demo.malindu.bookmanager.presentation.util.DefaultDispatcherProvider
import demo.malindu.bookmanager.presentation.util.DispatcherProvider

class AppContainer(
    databaseDriverFactory: DatabaseDriverFactory,
    httpClientFactory: HttpClientFactory,
    val dispatcherProvider: DispatcherProvider = DefaultDispatcherProvider
) {
    private val database: AppDatabase = AppDatabase(databaseDriverFactory.createDriver())
    private val localDataSource = FavoritesLocalDataSource(database)
    private val api = OpenLibraryApi(httpClientFactory.create())

    private val bookRepository: BookRepository = BookRepositoryImpl(api)
    private val favoritesRepository: FavoritesRepository = FavoritesRepositoryImpl(localDataSource)

    val searchBooks = SearchBooks(bookRepository)
    val getBookDetails = GetBookDetails(bookRepository)
    val observeFavorites = ObserveFavorites(favoritesRepository)
    val addFavorite = AddFavorite(favoritesRepository)
    val removeFavorite = RemoveFavorite(favoritesRepository)
    val isFavorite = IsFavorite(favoritesRepository)
}

expect fun createAppContainer(): AppContainer
