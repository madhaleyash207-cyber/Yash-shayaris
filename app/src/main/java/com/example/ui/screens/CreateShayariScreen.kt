package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material.icons.rounded.BookmarkAdd
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.model.ShayariCardTheme
import com.example.data.model.ShayariCategory
import com.example.data.model.ShayariEntity
import com.example.ui.components.ShayariCard
import com.example.ui.theme.DeepPlum
import com.example.ui.theme.RoyalPurple
import com.example.ui.theme.VibrantViolet
import com.example.ui.theme.WarmGold
import com.example.ui.theme.cardThemePalette
import com.example.ui.viewmodel.StudioDraftState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreateShayariScreen(
    draft: StudioDraftState,
    customShayaris: List<ShayariEntity>,
    onDraftTextChange: (String) -> Unit,
    onDraftAuthorChange: (String) -> Unit,
    onDraftCategoryChange: (ShayariCategory) -> Unit,
    onDraftThemeChange: (ShayariCardTheme) -> Unit,
    onSaveClick: () -> Unit,
    onClearDraft: () -> Unit,
    onCopy: (ShayariEntity) -> Unit,
    onShare: (ShayariEntity) -> Unit,
    onToggleFavorite: (ShayariEntity) -> Unit,
    onDeleteCustom: (ShayariEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val statusTop = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val previewEntity = ShayariEntity(
        id = -1,
        text = draft.text.ifBlank { stringResource(R.string.preview_placeholder_text) },
        author = draft.author.ifBlank { "यश" },
        category = draft.category.id,
        cardTheme = draft.cardTheme.id,
        isFavorite = false,
        isCustom = false
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Purple Studio Header
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(DeepPlum, RoyalPurple, VibrantViolet)
                        )
                    )
                    .padding(top = statusTop + 16.dp, start = 20.dp, end = 20.dp, bottom = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 760.dp)
                        .align(Alignment.Center)
                ) {
                    Text(
                        text = stringResource(R.string.create_title),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(R.string.create_subtitle),
                        style = MaterialTheme.typography.bodyMedium,
                        color = WarmGold
                    )
                }
            }
        }

        // Live Preview Card Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 760.dp)
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Visibility,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = stringResource(R.string.live_preview_label),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                ShayariCard(
                    shayari = previewEntity,
                    onCopy = onCopy,
                    onShare = onShare,
                    onToggleFavorite = {},
                    useGradientCardBackground = true,
                    modifier = Modifier.testTag("live_preview_card")
                )
            }
        }

        // Composer Form Card
        item {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 760.dp)
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    OutlinedTextField(
                        value = draft.text,
                        onValueChange = onDraftTextChange,
                        label = { Text(stringResource(R.string.input_shayari_label)) },
                        placeholder = { Text(stringResource(R.string.input_shayari_hint)) },
                        minLines = 4,
                        maxLines = 8,
                        isError = draft.errorRes != null,
                        supportingText = draft.errorRes?.let { errRes ->
                            { Text(text = stringResource(errRes)) }
                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("create_shayari_input")
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = draft.author,
                        onValueChange = onDraftAuthorChange,
                        label = { Text(stringResource(R.string.input_author_label)) },
                        placeholder = { Text(stringResource(R.string.input_author_hint)) },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("create_author_input")
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.select_category_label),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ShayariCategory.mainCategories.forEach { category ->
                            val selected = draft.category == category
                            FilterChip(
                                selected = selected,
                                onClick = { onDraftCategoryChange(category) },
                                label = {
                                    Text(
                                        text = "${stringResource(category.marathiTitleRes)} • ${stringResource(category.titleRes)}"
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                ),
                                modifier = Modifier.testTag("create_category_chip_${category.id.lowercase()}")
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.select_card_style_label),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ShayariCardTheme.entries.forEach { theme ->
                            val palette = cardThemePalette(theme)
                            val isSelected = draft.cardTheme == theme
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(
                                        Brush.linearGradient(
                                            listOf(palette.gradientStart, palette.gradientEnd)
                                        )
                                    )
                                    .then(
                                        if (isSelected) {
                                            Modifier.border(3.dp, WarmGold, CircleShape)
                                        } else {
                                            Modifier
                                        }
                                    )
                                    .clickable { onDraftThemeChange(theme) }
                                    .testTag("theme_swatch_${theme.id.lowercase()}")
                            ) {
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Rounded.Check,
                                        contentDescription = theme.label,
                                        tint = Color.White,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = onClearDraft,
                            shape = RoundedCornerShape(50),
                            modifier = Modifier
                                .weight(1f)
                                .minimumInteractiveComponentSize()
                                .testTag("clear_draft_button")
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Clear,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = stringResource(R.string.clear_draft_button))
                        }

                        Button(
                            onClick = onSaveClick,
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier
                                .weight(1.5f)
                                .minimumInteractiveComponentSize()
                                .testTag("save_shayari_button")
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.BookmarkAdd,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = stringResource(R.string.save_shayari_button))
                        }
                    }
                }
            }
        }

        // My Created Shayaris Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 760.dp)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.my_creations_title, customShayaris.size),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                if (customShayaris.isEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = RoundedCornerShape(18.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = stringResource(R.string.my_creations_empty),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }

        items(customShayaris, key = { it.id }) { shayari ->
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
                    onDelete = onDeleteCustom
                )
            }
        }
    }
}
