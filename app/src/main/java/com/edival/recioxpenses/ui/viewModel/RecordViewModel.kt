package com.edival.recioxpenses.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edival.recioxpenses.data.model.WorkDay
import com.edival.recioxpenses.domain.model.Resource
import com.edival.recioxpenses.domain.useCase.GetEverydayWorkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(private val getEverydayWorkUseCase: GetEverydayWorkUseCase) : ViewModel() {
    private val _inProgress = MutableLiveData<Boolean>()
    val inProgress: LiveData<Boolean> = _inProgress
    private val _getEverydayWorkRes = MutableLiveData<Resource<List<WorkDay>>?>()
    val getEverydayWorkRes: LiveData<Resource<List<WorkDay>>?> = _getEverydayWorkRes

    init {
        viewModelScope.launch {
            _inProgress.value = true
            getEverydayWorkUseCase().collect { resource ->
                _getEverydayWorkRes.value = resource
                _inProgress.value = false
            }
        }
    }

    fun cleanResources() {
        _getEverydayWorkRes.value = null
    }
}