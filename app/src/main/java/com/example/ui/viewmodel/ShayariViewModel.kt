package com.example.ui.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.R
import com.example.data.model.ShayariCardTheme
import com.example.data.model.ShayariCategory
import com.example.data.model.ShayariEntity
import com.example.data.repository.ShayariRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class StudioDraftState(
    val text: String = "",
    val author: String = "यश",
    val category: ShayariCategory = ShayariCategory.LOVE,
    val cardTheme: ShayariCardTheme = ShayariCardTheme.ROYAL_PURPLE,
    @param:StringRes val errorRes: Int? = null
)

data class ShayariUiState(
    val isLoading: Boolean = true,
    val allShayaris: List<ShayariEntity> = emptyList(),
    val favoriteShayaris: List<ShayariEntity> = emptyList(),
    val customShayaris: List<ShayariEntity> = emptyList(),
    val searchQuery: String = "",
    val dailyShayariIndex: Int = 0,
    val draft: StudioDraftState = StudioDraftState()
) {
    val dailyPool: List<ShayariEntity>
        get() {
            val featured = allShayaris.filter { it.isDailyFeatured }
            return featured.ifEmpty { allShayaris }
        }

    val currentDailyShayari: ShayariEntity?
        get() = dailyPool.takeIf { it.isNotEmpty() }?.let { pool ->
            pool[dailyShayariIndex % pool.size]
        }

    val filteredShayaris: List<ShayariEntity>
        get() {
            val q = searchQuery.trim().lowercase()
            if (q.isEmpty()) return emptyList()
            return allShayaris.filter { shayari ->
                shayari.text.lowercase().contains(q) ||
                    shayari.author.lowercase().contains(q) ||
                    shayari.category.lowercase().contains(q) ||
                    shayari.searchKeywords.lowercase().contains(q)
            }
        }

    fun shayarisForCategory(category: ShayariCategory): List<ShayariEntity> =
        allShayaris.filter { it.category == category.id }

    fun countForCategory(category: ShayariCategory): Int =
        allShayaris.count { it.category == category.id }
}

class ShayariViewModel(
    private val repository: ShayariRepository
) : ViewModel() {

    private val searchQueryFlow = MutableStateFlow("")
    private val dailyIndexFlow = MutableStateFlow(0)
    private val draftFlow = MutableStateFlow(StudioDraftState())
    private val isSeedReadyFlow = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            repository.ensureSeeded()
            isSeedReadyFlow.value = true
        }
    }

    val uiState: StateFlow<ShayariUiState> = combine(
        repository.allShayaris,
        repository.favoriteShayaris,
        repository.customShayaris,
        searchQueryFlow,
        dailyIndexFlow,
        draftFlow,
        isSeedReadyFlow
    ) { values ->
        @Suppress("UNCHECKED_CAST")
        val all = values[0] as List<ShayariEntity>
        @Suppress("UNCHECKED_CAST")
        val favorites = values[1] as List<ShayariEntity>
        @Suppress("UNCHECKED_CAST")
        val custom = values[2] as List<ShayariEntity>
        val query = values[3] as String
        val dailyIdx = values[4] as Int
        val draft = values[5] as StudioDraftState
        val ready = values[6] as Boolean

        ShayariUiState(
            isLoading = !ready && all.isEmpty(),
            allShayaris = all,
            favoriteShayaris = favorites,
            customShayaris = custom,
            searchQuery = query,
            dailyShayariIndex = dailyIdx,
            draft = draft
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ShayariUiState()
    )

    fun onSearchQueryChange(query: String) {
        searchQueryFlow.value = query
    }

    fun clearSearch() {
        searchQueryFlow.value = ""
    }

    fun nextDailyShayari() {
        dailyIndexFlow.update { it + 1 }
    }

    fun toggleFavorite(shayari: ShayariEntity) {
        viewModelScope.launch {
            repository.toggleFavorite(shayari)
        }
    }

    fun updateDraftText(text: String) {
        draftFlow.update { it.copy(text = text, errorRes = null) }
    }

    fun updateDraftAuthor(author: String) {
        draftFlow.update { it.copy(author = author) }
    }

    fun updateDraftCategory(category: ShayariCategory) {
        draftFlow.update { it.copy(category = category) }
    }

    fun updateDraftTheme(theme: ShayariCardTheme) {
        draftFlow.update { it.copy(cardTheme = theme) }
    }

    fun clearDraft() {
        draftFlow.update {
            StudioDraftState(
                text = "",
                author = it.author,
                category = it.category,
                cardTheme = it.cardTheme,
                errorRes = null
            )
        }
    }

    fun saveCustomShayari(onSaved: () -> Unit) {
        val currentDraft = draftFlow.value
        if (currentDraft.text.trim().isEmpty()) {
            draftFlow.update { it.copy(errorRes = R.string.error_empty_shayari) }
            return
        }
        viewModelScope.launch {
            repository.saveCustomShayari(
                text = currentDraft.text,
                author = currentDraft.author,
                categoryId = currentDraft.category.id,
                cardThemeId = currentDraft.cardTheme.id
            )
            draftFlow.update {
                it.copy(text = "", errorRes = null)
            }
            onSaved()
        }
    }

    fun deleteCustomShayari(id: Int) {
        viewModelScope.launch {
            repository.deleteCustomShayari(id)
        }
    }

    class Factory(private val repository: ShayariRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ShayariViewModel::class.java)) {
                return ShayariViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
