package com.example

import com.example.data.model.ShayariCategory
import com.example.data.model.ShayariSeedData
import com.example.ui.viewmodel.ShayariUiState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ShayariDataTest {
    @Test
    fun `seed data has exactly 6 Marathi shayaris for each of the 4 categories`() {
        val all = ShayariSeedData.curatedShayaris
        assertEquals(24, all.size)

        ShayariCategory.mainCategories.forEach { category ->
            val count = all.count { it.category == category.id }
            assertEquals("Expected 6 shayaris for ${category.id}", 6, count)
        }
    }

    @Test
    fun `search filters across Marathi text and keywords`() {
        val state = ShayariUiState(
            isLoading = false,
            allShayaris = ShayariSeedData.curatedShayaris,
            searchQuery = "dosti"
        )
        assertEquals(6, state.filteredShayaris.size)
        assertTrue(state.filteredShayaris.all { it.category == ShayariCategory.DOSTI.id })
    }
}
