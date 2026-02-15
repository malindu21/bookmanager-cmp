package demo.malindu.bookmanager.presentation.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.core.screen.Screen

class TabsScreen : Screen {
    @Composable
    override fun Content() {
        val snackbarHostState = remember { SnackbarHostState() }
        val booksTab = remember { BooksTab(snackbarHostState) }
        val favoritesTab = remember { FavoritesTab(snackbarHostState) }

        TabNavigator(booksTab) {
            Scaffold(
                snackbarHost = { SnackbarHost(snackbarHostState) },
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                bottomBar = {
                    Surface(
                        tonalElevation = 3.dp,
                        shadowElevation = 6.dp,
                        shape = RoundedCornerShape(24.dp),
                        color = MaterialTheme.colorScheme.surfaceContainerHigh,
                        modifier = Modifier
                            .fillMaxWidth()
                            .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Bottom))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            TabNavigationItem(tab = booksTab, modifier = Modifier.weight(1f))
                            TabNavigationItem(tab = favoritesTab, modifier = Modifier.weight(1f))
                        }
                    }
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Top + WindowInsetsSides.Horizontal
                            )
                        )
                        .padding(innerPadding)
                ) {
                    CurrentTab()
                }
            }
        }
    }
}

@Composable
private fun TabNavigationItem(tab: Tab, modifier: Modifier = Modifier) {
    val tabNavigator = cafe.adriel.voyager.navigator.tab.LocalTabNavigator.current
    val selected = tabNavigator.current == tab
    val containerColor =
        if (selected) MaterialTheme.colorScheme.primaryContainer
        else MaterialTheme.colorScheme.surfaceContainer
    val contentColor =
        if (selected) MaterialTheme.colorScheme.onPrimaryContainer
        else MaterialTheme.colorScheme.onSurfaceVariant

    Surface(
        modifier = modifier.clip(RoundedCornerShape(16.dp)),
        color = containerColor
    ) {
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { tabNavigator.current = tab },
            contentPadding = ButtonDefaults.TextButtonContentPadding
        ) {
            Text(
                text = tab.options.title,
                color = contentColor,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
            )
        }
    }
}
