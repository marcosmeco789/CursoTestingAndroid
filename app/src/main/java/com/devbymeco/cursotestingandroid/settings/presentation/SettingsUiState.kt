package com.devbymeco.cursotestingandroid.settings.presentation

import com.devbymeco.cursotestingandroid.core.domain.model.ThemeMode

data class SettingsUiState(
    val inStockOnly: Boolean = false,
    val themeMode: ThemeMode = ThemeMode.SYSTEM
)