package demo.malindu.bookmanager.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import demo.malindu.bookmanager.LocalAppContainer
import demo.malindu.bookmanager.domain.model.Book
import demo.malindu.bookmanager.presentation.components.ErrorState
import demo.malindu.bookmanager.presentation.components.ShimmerBox
import demo.malindu.bookmanager.presentation.util.UiEvent
import demo.malindu.bookmanager.presentation.util.rememberViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.flow.collectLatest

class DetailsScreen(private val book: Book) : Screen {
    @Composable
    override fun Content() {
        val container = LocalAppContainer.current
        val navigator = LocalNavigator.current
        val rootNavigator = navigator?.parent ?: navigator
        val snackbarHostState = SnackbarHostState()

        val viewModel = rememberViewModel {
            DetailsViewModel(
                bookStub = book,
                getBookDetails = container.getBookDetails,
                addFavorite = container.addFavorite,
                removeFavorite = container.removeFavorite,
                isFavorite = container.isFavorite,
                dispatcherProvider = container.dispatcherProvider
            )
        }

        LaunchedEffect(Unit) {
            viewModel.events.collectLatest { event ->
                if (event is UiEvent.ShowSnackbar) {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }

        val state by viewModel.state.collectAsState()

        Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) {
            when {
                state.isLoading -> {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .windowInsetsPadding(androidx.compose.foundation.layout.WindowInsets.safeDrawing)
                                .padding(16.dp)
                        ) {
                            ShimmerBox(modifier = Modifier.fillMaxWidth().height(240.dp))
                        }
                    }
                }
                state.error != null -> {
                    ErrorState(message = state.error ?: "Error", onRetry = viewModel::retry)
                }
                else -> {
                    val details = state.details
                    if (details != null) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .windowInsetsPadding(androidx.compose.foundation.layout.WindowInsets.safeDrawing)
                                    .padding(16.dp)
                            ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                FilledTonalButton(
                                    onClick = { rootNavigator?.pop() },
                                    contentPadding = ButtonDefaults.ContentPadding,
                                    colors = ButtonDefaults.filledTonalButtonColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = MaterialTheme.colorScheme.onPrimary
                                    )
                                ) {
                                    Text("Back")
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                FilledTonalButton(
                                    onClick = viewModel::toggleFavorite,
                                    contentPadding = ButtonDefaults.ContentPadding,
                                    colors = ButtonDefaults.filledTonalButtonColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = MaterialTheme.colorScheme.onPrimary
                                    )
                                ) {
                                    Text(if (state.isFavorite) "Saved" else "Save")
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(260.dp),
                                shape = MaterialTheme.shapes.large,
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer
                                )
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(MaterialTheme.shapes.large)
                                ) {
                                    val coverUrl = details.coverUrl
                                    if (coverUrl != null) {
                                        // Blurred background image
                                        KamelImage(
                                            resource = asyncPainterResource(coverUrl),
                                            contentDescription = details.title,
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .blur(22.dp)
                                        )
                                        // Soft overlay to simulate blur and deepen navy tone
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(
                                                    Brush.verticalGradient(
                                                        listOf(
                                                            Color(0xAA0B1B3A),
                                                            Color(0x662563EB),
                                                            Color(0x990B1B3A)
                                                        )
                                                    )
                                                )
                                        )
                                        // Foreground poster
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(16.dp)
                                                .clip(MaterialTheme.shapes.medium)
                                        ) {
                                            KamelImage(
                                                resource = asyncPainterResource(coverUrl),
                                                contentDescription = details.title,
                                                modifier = Modifier.fillMaxSize()
                                            )
                                        }
                                    } else {
                                        ShimmerBox(modifier = Modifier.fillMaxSize())
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(details.title, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onBackground)
                            if (details.authors.isNotEmpty()) {
                                Text(details.authors.joinToString(), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            details.firstPublishYear?.let {
                                Text(it.toString(), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            if (details.subjects.isNotEmpty()) {
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = MaterialTheme.shapes.large,
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                                    )
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(
                                            "Categories",
                                            style = MaterialTheme.typography.titleSmall,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                            items(details.subjects.take(12)) { subject ->
                                                Card(
                                                    modifier = Modifier.wrapContentHeight(),
                                                    shape = MaterialTheme.shapes.small,
                                                    colors = CardDefaults.cardColors(
                                                        containerColor = MaterialTheme.colorScheme.primaryContainer
                                                    )
                                                ) {
                                                    Text(
                                                        subject,
                                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                                        style = MaterialTheme.typography.labelMedium,
                                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                            }

                            if (!details.description.isNullOrBlank()) {
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = MaterialTheme.shapes.large,
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                                    )
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(
                                            "Description",
                                            style = MaterialTheme.typography.titleSmall,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            details.description,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                        }
                    }
                }
            }
        }
    }
}
