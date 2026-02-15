package demo.malindu.bookmanager

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.transitions.SlideTransition
import demo.malindu.bookmanager.di.AppContainer
import demo.malindu.bookmanager.di.createAppContainer
import demo.malindu.bookmanager.presentation.app.TabsScreen
import demo.malindu.bookmanager.presentation.theme.AppTheme

val LocalAppContainer = staticCompositionLocalOf<AppContainer> {
    error("AppContainer not provided")
}

@Composable
fun App() {
    val container = remember { createAppContainer() }
    AppTheme(darkTheme = isSystemInDarkTheme()) {
        CompositionLocalProvider(LocalAppContainer provides container) {
            Navigator(
                TabsScreen(),
                disposeBehavior = NavigatorDisposeBehavior(
                    disposeNestedNavigators = false,
                    disposeSteps = false
                )
            ) { navigator ->
                SlideTransition(navigator)
            }
        }
    }
}
