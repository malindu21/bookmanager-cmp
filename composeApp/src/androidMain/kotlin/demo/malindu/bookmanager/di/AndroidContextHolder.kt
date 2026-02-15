package demo.malindu.bookmanager.di

import android.content.Context

object AndroidContextHolder {
    var context: Context? = null
        private set

    fun init(context: Context) {
        this.context = context.applicationContext
    }
}
