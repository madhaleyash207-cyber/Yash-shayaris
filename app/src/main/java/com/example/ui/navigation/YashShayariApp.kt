package com.example.ui.navigation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.R
import com.example.data.model.ShayariCategory
import com.example.data.model.ShayariEntity
import com.example.ui.screens.CategoryDetailScreen
import com.example.ui.screens.CreateShayariScreen
import com.example.ui.screens.FavoritesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.viewmodel.ShayariViewModel
import kotlinx.coroutines.launch

object AppRoutes {
    const val HOME = "home"
    const val FAVORITES = "favorites"
    const val CREATE = "create"
    const val CATEGORY_BASE = "category"
    const val CATEGORY_PATTERN = "category/{categoryId}"

    fun categoryRoute(category: ShayariCategory): String = "$CATEGORY_BASE/${category.id}"
}

private data class TopLevelDestination(
    val route: String,
    val labelRes: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

private val topLevelDestinations = listOf(
    TopLevelDestination(
        route = AppRoutes.HOME,
        labelRes = R.string.nav_home,
        selectedIcon = Icons.Rounded.Home,
        unselectedIcon = Icons.Outlined.Home,
        testTag = "nav_home"
    ),
    TopLevelDestination(
        route = AppRoutes.FAVORITES,
        labelRes = R.string.nav_favorites,
        selectedIcon = Icons.Rounded.Favorite,
        unselectedIcon = Icons.Outlined.FavoriteBorder,
        testTag = "nav_favorites"
    ),
    TopLevelDestination(
        route = AppRoutes.CREATE,
        labelRes = R.string.nav_create,
        selectedIcon = Icons.Rounded.EditNote,
        unselectedIcon = Icons.Outlined.EditNote,
        testTag = "nav_create"
    )
)

@Composable
fun YashShayariApp(
    viewModel: ShayariViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isTopLevel = currentDestination?.route in listOf(
        AppRoutes.HOME,
        AppRoutes.FAVORITES,
        AppRoutes.CREATE
    )

    val copiedMsg = stringResource(R.string.copied_snackbar)
    val savedMsg = stringResource(R.string.saved_snackbar)
    val deletedMsg = stringResource(R.string.deleted_snackbar)
    val favAddedMsg = stringResource(R.string.favorite_added_snackbar)
    val favRemovedMsg = stringResource(R.string.favorite_removed_snackbar)
    val shareTitle = stringResource(R.string.share_chooser_title)

    fun showMessage(msg: String) {
        coroutineScope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(msg)
        }
    }

    fun copyShayari(shayari: ShayariEntity) {
        val formatted = "${shayari.text}\n\n— ${shayari.author}\n(Yash Shayari)"
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("Marathi Shayari", formatted))
        showMessage(copiedMsg)
    }

    fun shareShayari(shayari: ShayariEntity) {
        val formatted = "${shayari.text}\n\n— ${shayari.author}\n✨ Shared via Yash Shayari"
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, formatted)
        }
        context.startActivity(Intent.createChooser(sendIntent, shareTitle))
    }

    fun toggleFavoriteWithFeedback(shayari: ShayariEntity) {
        val willBeFav = !shayari.isFavorite
        viewModel.toggleFavorite(shayari)
        showMessage(if (willBeFav) favAddedMsg else favRemovedMsg)
    }

    fun navigateTopLevel(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val useNavRail = maxWidth >= 600.dp

        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                if (isTopLevel && !useNavRail) {
                    NavigationBar(
                        windowInsets = WindowInsets.navigationBars,
                        containerColor = MaterialTheme.colorScheme.surface
                    ) {
                        topLevelDestinations.forEach { dest ->
                            val selected = currentDestination?.hierarchy?.any {
                                it.route == dest.route
                            } == true

                            NavigationBarItem(
                                selected = selected,
                                onClick = { navigateTopLevel(dest.route) },
                                icon = {
                                    if (dest.route == AppRoutes.FAVORITES && uiState.favoriteShayaris.isNotEmpty()) {
                                        BadgedBox(
                                            badge = {
                                                Badge {
                                                    Text(uiState.favoriteShayaris.size.toString())
                                                }
                                            }
                                        ) {
                                            Icon(
                                                imageVector = if (selected) dest.selectedIcon else dest.unselectedIcon,
                                                contentDescription = stringResource(dest.labelRes)
                                            )
                                        }
                                    } else {
                                        Icon(
                                            imageVector = if (selected) dest.selectedIcon else dest.unselectedIcon,
                                            contentDescription = stringResource(dest.labelRes)
                                        )
                                    }
                                },
                                label = { Text(stringResource(dest.labelRes)) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                                ),
                                modifier = Modifier.testTag(dest.testTag)
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (isTopLevel && useNavRail) {
                    NavigationRail(
                        containerColor = MaterialTheme.colorScheme.surface
                    ) {
                        topLevelDestinations.forEach { dest ->
                            val selected = currentDestination?.hierarchy?.any {
                                it.route == dest.route
                            } == true
                            NavigationRailItem(
                                selected = selected,
                                onClick = { navigateTopLevel(dest.route) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) dest.selectedIcon else dest.unselectedIcon,
                                        contentDescription = stringResource(dest.labelRes)
                                    )
                                },
                                label = { Text(stringResource(dest.labelRes)) },
                                colors = NavigationRailItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                                ),
                                modifier = Modifier.testTag(dest.testTag)
                            )
                        }
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = AppRoutes.HOME,
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable(AppRoutes.HOME) {
                        HomeScreen(
                            uiState = uiState,
                            onSearchQueryChange = viewModel::onSearchQueryChange,
                            onClearSearch = viewModel::clearSearch,
                            onNextDailyShayari = viewModel::nextDailyShayari,
                            onCategoryClick = { category ->
                                navController.navigate(AppRoutes.categoryRoute(category))
                            },
                            onOpenFavorites = { navigateTopLevel(AppRoutes.FAVORITES) },
                            onOpenCreateStudio = { navigateTopLevel(AppRoutes.CREATE) },
                            onCopy = ::copyShayari,
                            onShare = ::shareShayari,
                            onToggleFavorite = ::toggleFavoriteWithFeedback,
                            onDeleteCustom = { shayari ->
                                viewModel.deleteCustomShayari(shayari.id)
                                showMessage(deletedMsg)
                            }
                        )
                    }

                    composable(
                        route = AppRoutes.CATEGORY_PATTERN,
                        arguments = listOf(
                            navArgument("categoryId") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        val catId = backStackEntry.arguments?.getString("categoryId") ?: ShayariCategory.LOVE.id
                        val category = ShayariCategory.fromId(catId)
                        CategoryDetailScreen(
                            category = category,
                            shayaris = uiState.shayarisForCategory(category),
                            onBackClick = { navController.popBackStack() },
                            onCopy = ::copyShayari,
                            onShare = ::shareShayari,
                            onToggleFavorite = ::toggleFavoriteWithFeedback,
                            onDeleteCustom = { shayari ->
                                viewModel.deleteCustomShayari(shayari.id)
                                showMessage(deletedMsg)
                            }
                        )
                    }

                    composable(AppRoutes.FAVORITES) {
                        FavoritesScreen(
                            favorites = uiState.favoriteShayaris,
                            onExploreClick = { navigateTopLevel(AppRoutes.HOME) },
                            onCopy = ::copyShayari,
                            onShare = ::shareShayari,
                            onToggleFavorite = ::toggleFavoriteWithFeedback,
                            onDeleteCustom = { shayari ->
                                viewModel.deleteCustomShayari(shayari.id)
                                showMessage(deletedMsg)
                            }
                        )
                    }

                    composable(AppRoutes.CREATE) {
                        CreateShayariScreen(
                            draft = uiState.draft,
                            customShayaris = uiState.customShayaris,
                            onDraftTextChange = viewModel::updateDraftText,
                            onDraftAuthorChange = viewModel::updateDraftAuthor,
                            onDraftCategoryChange = viewModel::updateDraftCategory,
                            onDraftThemeChange = viewModel::updateDraftTheme,
                            onSaveClick = {
                                viewModel.saveCustomShayari {
                                    showMessage(savedMsg)
                                }
                            },
                            onClearDraft = viewModel::clearDraft,
                            onCopy = ::copyShayari,
                            onShare = ::shareShayari,
                            onToggleFavorite = ::toggleFavoriteWithFeedback,
                            onDeleteCustom = { shayari ->
                                viewModel.deleteCustomShayari(shayari.id)
                                showMessage(deletedMsg)
                            }
                        )
                    }
                }
            }
        }
    }
}
