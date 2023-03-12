package com.technomatesoftware.ergostats.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technomatesoftware.ergostats.domain.interfaces.CoinGeckoRepository
import com.technomatesoftware.ergostats.domain.models.CoinGeckoModel
import com.technomatesoftware.ergostats.domain.models.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val coinGeckoRepository: CoinGeckoRepository,
): ViewModel() {
    private val _gamesState = mutableStateOf<Response<List<CoinGeckoModel>>>(Response.Success(null))
    val gamesState: State<Response<List<CoinGeckoModel>>> = _gamesState

    fun coinMarketData(){
        viewModelScope.launch {
            coinGeckoRepository.getCoinMarketData().collect { response ->
                _gamesState.value = response

                when( val test = _gamesState.value) {
                    is Response.Loading -> {
                        Log.d("Testing", "loading")
                    }

                    is Response.Success -> {
                        test.data?.first()?.let { Log.d("Testing", it.name) }
                    }

                    is Response.Failure -> {
                        Log.d("Testing", "failed")
                    }
                }
            }
        }
    }
}