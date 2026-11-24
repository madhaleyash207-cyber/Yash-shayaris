package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.FormatQuote
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
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
import com.example.ui.theme.FavoriteHeartPink
import com.example.ui.theme.ShayariVerseStyle
import com.example.ui.theme.cardThemePalette

@Composable
fun ShayariCard(
    shayari: ShayariEntity,
    onCopy: (ShayariEntity) -> Unit,
    onShare: (ShayariEntity) -> Unit,
    onToggleFavorite: (ShayariEntity) -> Unit,
    modifier: Modifier = Modifier,
    onDelete: ((ShayariEntity) -> Unit)? = null,
    useGradientCardBackground: Boolean = false
) {
    val theme = ShayariCardTheme.fromId(shayari.cardTheme)
    val palette = cardThemePalette(theme)
    val category = ShayariCategory.fromId(shayari.category)

    val heartScale by animateFloatAsState(
        targetValue = if (shayari.isFavorite) 1.2f else 1.0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "favorite_heart_scale"
    )
    val heartColor by animateColorAsState(
        targetValue = if (shayari.isFavorite) {
            if (useGradientCardBackground) Color.White else FavoriteHeartPink
        } else {
            if (useGradientCardBackground) Color.White.copy(alpha = 0.85f)
            else MaterialTheme.colorScheme.onSurfaceVariant
        },
        label = "favorite_heart_color"
    )

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .testTag("shayari_card_${shayari.id}"),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (useGradientCardBackground) {
                        Modifier.background(
                            Brush.linearGradient(
                                colors = listOf(palette.gradientStart, palette.gradientEnd)
                            )
                        )
                    } else {
                        Modifier
                    }
                )
        ) {
            // Colorful top ribbon bar when using surface-backed card
            if (!useGradientCardBackground) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(palette.gradientStart, palette.gradientEnd)
                            )
                        )
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                // Header row: Category badge + Quote/Delete
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = if (useGradientCardBackground) {
                            Color.White.copy(alpha = 0.22f)
                        } else {
                            palette.gradientStart.copy(alpha = 0.12f)
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (useGradientCardBackground) Color.White
                                        else palette.gradientStart
                                    )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${stringResource(category.marathiTitleRes)} • ${stringResource(category.titleRes)}",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (useGradientCardBackground) Color.White else palette.gradientStart
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (shayari.isCustom && onDelete != null) {
                            IconButton(
                                onClick = { onDelete(shayari) },
                                modifier = Modifier
                                    .minimumInteractiveComponentSize()
                                    .testTag("delete_shayari_button_${shayari.id}")
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.DeleteOutline,
                                    contentDescription = stringResource(R.string.action_delete),
                                    tint = if (useGradientCardBackground) Color.White.copy(alpha = 0.85f)
                                    else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        } else {
                            Icon(
                                imageVector = Icons.Rounded.FormatQuote,
                                contentDescription = null,
                                tint = if (useGradientCardBackground) Color.White.copy(alpha = 0.35f)
                                else palette.gradientStart.copy(alpha = 0.25f),
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Marathi Shayari Verse
                Text(
                    text = shayari.text,
                    style = ShayariVerseStyle,
                    color = if (useGradientCardBackground) Color.White else MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Author Signature
                Text(
                    text = "— ${shayari.author}",
                    style = MaterialTheme.typography.labelLarge,
                    color = if (useGradientCardBackground) Color.White.copy(alpha = 0.9f)
                    else palette.gradientStart,
                    modifier = Modifier.align(Alignment.End)
                )

                Spacer(modifier = Modifier.height(14.dp))

                HorizontalDivider(
                    color = if (useGradientCardBackground) Color.White.copy(alpha = 0.22f)
                    else MaterialTheme.colorScheme.outline.copy(alpha = 0.45f)
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Action buttons row: Copy, Share, Favorite Heart
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = { onCopy(shayari) },
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .testTag("copy_button_${shayari.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ContentCopy,
                                contentDescription = stringResource(R.string.action_copy),
                                tint = if (useGradientCardBackground) Color.White
                                else MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = stringResource(R.string.action_copy),
                                style = MaterialTheme.typography.labelLarge,
                                color = if (useGradientCardBackground) Color.White
                                else MaterialTheme.colorScheme.primary
                            )
                        }

                        TextButton(
                            onClick = { onShare(shayari) },
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .testTag("share_button_${shayari.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Share,
                                contentDescription = stringResource(R.string.action_share),
                                tint = if (useGradientCardBackground) Color.White
                                else palette.gradientStart,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = stringResource(R.string.action_share),
                                style = MaterialTheme.typography.labelLarge,
                                color = if (useGradientCardBackground) Color.White
                                else palette.gradientStart
                            )
                        }
                    }

                    IconButton(
                        onClick = { onToggleFavorite(shayari) },
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("favorite_button_${shayari.id}")
                    ) {
                        Icon(
                            imageVector = if (shayari.isFavorite) {
                                Icons.Rounded.Favorite
                            } else {
                                Icons.Rounded.FavoriteBorder
                            },
                            contentDescription = if (shayari.isFavorite) {
                                stringResource(R.string.action_unfavorite)
                            } else {
                                stringResource(R.string.action_favorite)
                            },
                            tint = heartColor,
                            modifier = Modifier
                                .size(24.dp)
                                .scale(heartScale)
                        )
                    }
                }
            }
        }
    }
}
