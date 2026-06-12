package com.devbymeco.cursotestingandroid.settings.presentation

import android.provider.SyncStateContract.Helpers.update
import app.cash.turbine.test
import com.devbymeco.cursotestingandroid.core.MainDispatcherRule
import com.devbymeco.cursotestingandroid.core.domain.model.ThemeMode
import com.devbymeco.cursotestingandroid.core.fakes.FakeSettingsRepository
import com.devbymeco.cursotestingandroid.productlist.domain.repository.SettingsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun given_repository_with_values_when_viewmodel_is_initialized_then_ui_state_is_updated() =
        runTest(mainDispatcherRule.scheduler) {

            //Given
            val settingRepository = FakeSettingsRepository().apply {
                setInStockOnly(true)
            }

            //When
            val viewModel = SettingsViewModel(settingRepository)

            //Then
            viewModel.uiState.test {
                val state = awaitItem()
                assertTrue(state.inStockOnly)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun given_viewmodel_when_theme_mode_is_changed_then_ui_state_and_repository_are_updated() =
        runTest(mainDispatcherRule.scheduler) {

            //Given
            val settingsRepository = FakeSettingsRepository()
            val viewModel = SettingsViewModel(settingsRepository)

            viewModel.uiState.test {
                awaitItem()

                //When
                viewModel.setThemeMode(ThemeMode.DARK)

                //Then
                val updateState = awaitItem()
                assertEquals(ThemeMode.DARK, updateState.themeMode)
                assertEquals(ThemeMode.DARK, settingsRepository.themeMode.first())
                cancelAndIgnoreRemainingEvents()
            }
        }


    @Test
    fun given_viewmodel_when_in_stock_only_is_changed_then_ui_state_and_repository_are_updated() =
        runTest(mainDispatcherRule.scheduler) {

            //Given
            val settingsRepository = FakeSettingsRepository()
            val viewModel = SettingsViewModel(settingsRepository)

            viewModel.uiState.test {
                awaitItem()

                //When
                viewModel.setInStockOnly(true)

                //Then
                val updateState = awaitItem()
                assertTrue(updateState.inStockOnly)
                assertTrue(settingsRepository.inStockOnly.first())
                cancelAndIgnoreRemainingEvents()
            }
        }


    @Test
    fun given_viewmodel_when_repository_change_externally_when_ui_state_update_automatically() = runTest(mainDispatcherRule.scheduler) {
        //Given
        val settingsRepository = FakeSettingsRepository()
        val viewModel = SettingsViewModel(settingsRepository)

        viewModel.uiState.test {
            awaitItem()

            settingsRepository.setInStockOnly(true)

            assertTrue(awaitItem().inStockOnly)
            cancelAndIgnoreRemainingEvents()
        }
    }
}