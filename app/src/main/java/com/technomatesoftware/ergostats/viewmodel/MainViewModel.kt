package com.technomatesoftware.ergostats.viewmodel

import androidx.lifecycle.ViewModel
import com.technomatesoftware.ergostats.MainViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class MainViewModel : ViewModel() {
    private val _viewState = MutableStateFlow(MainViewState())
    val viewState: StateFlow<MainViewState> = _viewState

    fun setTitle(title: String) {
        _viewState.value = _viewState.value.copy(appBarTitle = title)
    }

    fun setHideBottomNavBar(isHidden: Boolean) {
        _viewState.value = _viewState.value.copy(hideBottomNavBar = isHidden)
    }

    fun setEnableBackButton(isEnabled: Boolean) {
        _viewState.value = _viewState.value.copy(enableBackButton = isEnabled)
    }
}

object MainViewModelSingleton {
    val viewModel = MainViewModel()
}