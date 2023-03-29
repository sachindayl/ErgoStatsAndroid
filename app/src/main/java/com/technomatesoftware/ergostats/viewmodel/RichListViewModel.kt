package com.technomatesoftware.ergostats.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technomatesoftware.ergostats.domain.models.Response
import com.technomatesoftware.ergostats.domain.states.RichListViewState
import com.technomatesoftware.ergostats.network.interfaces.ErgoWatchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RichListViewModel @Inject constructor(
    private val ergoWatchRepository: ErgoWatchRepository
) : ViewModel() {
    private val _viewState = MutableStateFlow(RichListViewState())
    val viewState: StateFlow<RichListViewState> = _viewState

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            richListData()
        }
    }

    private suspend fun richListData() {
        ergoWatchRepository.getTop100RichList().collect { response ->
            Log.d("Rich List", "getting reposnse")
            when (response) {
                is Response.Loading -> {
                    _viewState.value = _viewState.value.copy(isDataLoading = true)
                }

                is Response.Success -> {
                    response.data?.first()?.address?.let { Log.d("Rich List", it) }
                    _viewState.value =
                        _viewState.value.copy(
                            isDataLoading = false,
                            richList = response.data ?: emptyList()
                        )
                }

                is Response.Failure -> {
                    Log.d("Rich List", response.e.toString())
                    _viewState.value = _viewState.value.copy(isDataLoading = false)
                }
            }
        }
    }
}