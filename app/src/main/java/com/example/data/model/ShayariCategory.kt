package com.example.data.model

import androidx.annotation.StringRes
import com.example.R

enum class ShayariCategory(
    val id: String,
    @param:StringRes val titleRes: Int,
    @param:StringRes val marathiTitleRes: Int,
    @param:StringRes val descriptionRes: Int
) {
    LOVE(
        id = "LOVE",
        titleRes = R.string.cat_love_title,
        marathiTitleRes = R.string.cat_love_marathi,
        descriptionRes = R.string.cat_love_desc
    ),
    SAD(
        id = "SAD",
        titleRes = R.string.cat_sad_title,
        marathiTitleRes = R.string.cat_sad_marathi,
        descriptionRes = R.string.cat_sad_desc
    ),
    DOSTI(
        id = "DOSTI",
        titleRes = R.string.cat_dosti_title,
        marathiTitleRes = R.string.cat_dosti_marathi,
        descriptionRes = R.string.cat_dosti_desc
    ),
    ATTITUDE(
        id = "ATTITUDE",
        titleRes = R.string.cat_attitude_title,
        marathiTitleRes = R.string.cat_attitude_marathi,
        descriptionRes = R.string.cat_attitude_desc
    ),
    CUSTOM(
        id = "CUSTOM",
        titleRes = R.string.cat_custom_title,
        marathiTitleRes = R.string.cat_custom_marathi,
        descriptionRes = R.string.create_subtitle
    );

    companion object {
        val mainCategories: List<ShayariCategory> = listOf(LOVE, SAD, DOSTI, ATTITUDE)

        fun fromId(id: String): ShayariCategory =
            entries.firstOrNull { it.id.equals(id, ignoreCase = true) } ?: LOVE
    }
}

enum class ShayariCardTheme(val id: String, val label: String) {
    ROYAL_PURPLE("ROYAL_PURPLE", "Royal"),
    ROSE_LOVE("ROSE_LOVE", "Rose"),
    INDIGO_SAD("INDIGO_SAD", "Midnight"),
    EMERALD_DOSTI("EMERALD_DOSTI", "Emerald"),
    AMBER_ATTITUDE("AMBER_ATTITUDE", "Gold");

    companion object {
        fun fromId(id: String): ShayariCardTheme =
            entries.firstOrNull { it.id == id } ?: ROYAL_PURPLE

        fun defaultForCategory(category: ShayariCategory): ShayariCardTheme = when (category) {
            ShayariCategory.LOVE -> ROSE_LOVE
            ShayariCategory.SAD -> INDIGO_SAD
            ShayariCategory.DOSTI -> EMERALD_DOSTI
            ShayariCategory.ATTITUDE -> AMBER_ATTITUDE
            ShayariCategory.CUSTOM -> ROYAL_PURPLE
        }
    }
}
