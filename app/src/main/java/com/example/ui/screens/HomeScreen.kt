package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoFixHigh
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.SearchOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.model.ShayariCategory
import com.example.data.model.ShayariEntity
import com.example.ui.components.CategoryGrid
import com.example.ui.components.DailyShayariBanner
import com.example.ui.components.ShayariCard
import com.example.ui.theme.DeepPlum
import com.example.ui.theme.FavoriteHeartPink
import com.example.ui.theme.OrchidGlow
import com.example.ui.theme.RoyalPurple
import com.example.ui.theme.VibrantViolet
import com.example.ui.theme.WarmGold
import com.example.ui.viewmodel.ShayariUiState

@Composable
fun HomeScreen(
    uiState: ShayariUiState,
    onSearchQueryChange: (String) -> Unit,
    onClearSearch: () -> Unit,
    onNextDailyShayari: () -> Unit,
    onCategoryClick: (ShayariCategory) -> Unit,
    onOpenFavorites: () -> Unit,
    onOpenCreateStudio: () -> Unit,
    onCopy: (ShayariEntity) -> Unit,
    onShare: (ShayariEntity) -> Unit,
    onToggleFavorite: (ShayariEntity) -> Unit,
    onDeleteCustom: (ShayariEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val categoryCounts = ShayariCategory.mainCategories.associateWith { category ->
        uiState.countForCategory(category)
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val isWide = maxWidth >= 600.dp

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Purple Header + Overlapping Search Bar
            item {
                PurpleHomeHeaderWithSearch(
                    favoritesCount = uiState.favoriteShayaris.size,
                    searchQuery = uiState.searchQuery,
                    onSearchQueryChange = onSearchQueryChange,
                    onClearSearch = onClearSearch,
                    onOpenFavorites = onOpenFavorites
                )
            }

            if (uiState.searchQuery.isNotBlank()) {
                val results = uiState.filteredShayaris
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .widthIn(max = 760.dp)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.search_results_title, results.size),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                if (results.isEmpty()) {
                    item {
                        EmptySearchCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .widthIn(max = 760.dp)
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                        )
                    }
                } else {
                    items(results, key = { it.id }) { shayari ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .widthIn(max = 760.dp)
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            ShayariCard(
                                shayari = shayari,
                                onCopy = onCopy,
                                onShare = onShare,
                                onToggleFavorite = onToggleFavorite,
                                onDelete = if (shayari.isCustom) onDeleteCustom else null
                            )
                        }
                    }
                }
            } else {
                // Daily Shayari Banner
                uiState.currentDailyShayari?.let { daily ->
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .widthIn(max = 760.dp)
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            DailyShayariBanner(
                                shayari = daily,
                                onNextDaily = onNextDailyShayari,
                                onCopy = onCopy,
                                onShare = onShare,
                                onToggleFavorite = onToggleFavorite
                            )
                        }
                    }
                }

                // 4 Colorful Categories Section
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .widthIn(max = 760.dp)
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Column {
                                Text(
                                    text = stringResource(R.string.categories_section_title),
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = stringResource(R.string.categories_section_subtitle),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        CategoryGrid(
                            counts = categoryCounts,
                            onCategoryClick = onCategoryClick,
                            columns = if (isWide) 4 else 2
                        )
                    }
                }

                // "Create Your Own Shayari" CTA Card
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .widthIn(max = 760.dp)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        CreateShayariCtaBanner(onClick = onOpenCreateStudio)
                    }
                }
            }
        }
    }
}

@Composable
private fun PurpleHomeHeaderWithSearch(
    favoritesCount: Int,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onClearSearch: () -> Unit,
    onOpenFavorites: () -> Unit
) {
    val statusTop = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    Box(modifier = Modifier.fillMaxWidth()) {
        // Purple gradient header backdrop
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(DeepPlum, RoyalPurple, VibrantViolet)
                    )
                )
                .padding(top = statusTop + 16.dp, start = 20.dp, end = 20.dp, bottom = 46.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 760.dp)
                    .align(Alignment.Center),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.displaySmall,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.app_tagline),
                        style = MaterialTheme.typography.bodyMedium,
                        color = WarmGold
                    )
                }

                Surface(
                    onClick = onOpenFavorites,
                    shape = RoundedCornerShape(50),
                    color = Color.White.copy(alpha = 0.16f),
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .testTag("header_favorites_badge")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Favorite,
                            contentDescription = stringResource(R.string.nav_favorites),
                            tint = FavoriteHeartPink,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = favoritesCount.toString(),
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // Overlapping Search Bar
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 760.dp)
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp)
                .offset(y = 14.dp),
            shape = RoundedCornerShape(28.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.search_placeholder),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(
                            onClick = onClearSearch,
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .testTag("clear_search_button")
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = stringResource(R.string.clear_search),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("search_input")
            )
        }
    }

    // Reserve breathing space for the overlapping search card
    Spacer(modifier = Modifier.height(22.dp))
}

@Composable
private fun CreateShayariCtaBanner(
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("home_create_shayari_button"),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(RoyalPurple, OrchidGlow)
                    )
                )
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = WarmGold.copy(alpha = 0.22f),
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Icon(
                            imageVector = Icons.Rounded.EditNote,
                            contentDescription = null,
                            tint = WarmGold,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = stringResource(R.string.home_create_title),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = stringResource(R.string.home_create_subtitle),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.88f)
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Button(
                onClick = onClick,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = WarmGold,
                    contentColor = DeepPlum
                ),
                modifier = Modifier.minimumInteractiveComponentSize()
            ) {
                Icon(
                    imageVector = Icons.Rounded.AutoFixHigh,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = stringResource(R.string.home_create_button),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
private fun EmptySearchCard(modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Rounded.SearchOff,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(44.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.search_empty_title),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.search_empty_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}
