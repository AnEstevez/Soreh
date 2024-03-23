package com.andresestevez.soreh.ui.screens.characters.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.andresestevez.soreh.domain.GetRandomCharactersListUseCase
import com.andresestevez.soreh.framework.workers.CacheWorker
import com.andresestevez.soreh.ui.common.getUserMessage
import com.andresestevez.soreh.ui.screens.common.ItemUiState
import com.andresestevez.soreh.ui.screens.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRandomCharactersList: GetRandomCharactersListUseCase,
    workManager: WorkManager
) :
    ViewModel() {

    private val workInfo =
        workManager.getWorkInfosForUniqueWorkLiveData(CacheWorker.WORK_NAME).map { it[0] }.asFlow()

    private var _uiState = MutableStateFlow(UiState())
    var uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            workInfo.collect{
                if(it.state.isFinished) refresh()
            }
        }
    }

    private fun refresh() {
        viewModelScope.launch {
            _uiState.value = UiState(loading = true)
            getRandomCharactersList(10).fold({ characters ->
                _uiState.update { currentState ->
                    currentState.copy(
                        data = characters.map { character -> ItemUiState(character = character) },
                        loading = false
                    )
                }
            }) {
                _uiState.update { currentState ->
                    Timber.e(it)
                    currentState.copy(loading = false, userMessage = it.getUserMessage())
                }
            }
        }
    }

    fun dismissUserMessage() {
        _uiState.update { it.copy(userMessage = "") }
    }
}

