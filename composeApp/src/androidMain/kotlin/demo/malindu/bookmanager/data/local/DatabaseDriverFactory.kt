package demo.malindu.bookmanager.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        val context = demo.malindu.bookmanager.di.AndroidContextHolder.context
            ?: error("Android context not initialized")
        return AndroidSqliteDriver(AppDatabase.Schema, context, "books.db")
    }
}
