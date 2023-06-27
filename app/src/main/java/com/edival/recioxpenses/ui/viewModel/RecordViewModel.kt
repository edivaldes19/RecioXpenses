package com.edival.recioxpenses.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edival.recioxpenses.data.model.WorkDay
import com.edival.recioxpenses.domain.model.Resource
import com.edival.recioxpenses.domain.useCase.DeleteWorkDayUseCase
import com.edival.recioxpenses.domain.useCase.GetEverydayWorkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordViewModel @Inject constructor(
    private val getEverydayWorkUseCase: GetEverydayWorkUseCase,
    private val deleteWorkDayUseCase: DeleteWorkDayUseCase,
) : ViewModel() {
    private val _inProgress = MutableLiveData<Boolean>()
    val inProgress: LiveData<Boolean> = _inProgress
    private val _getEverydayWorkRes = MutableLiveData<Resource<List<WorkDay>>>()
    val getEverydayWorkRes: LiveData<Resource<List<WorkDay>>> = _getEverydayWorkRes
    private val _deleteWorkDayRes = MutableLiveData<Resource<Unit>>()
    val deleteWorkDayRes: LiveData<Resource<Unit>> = _deleteWorkDayRes

    init {
        viewModelScope.launch {
            _inProgress.value = true
            getEverydayWorkUseCase()
                .catch { error ->
                    _getEverydayWorkRes.value = Resource.Error(error.message)
                    _inProgress.value = false
                }.collect { resource ->
                    _getEverydayWorkRes.value = resource
                    _inProgress.value = false
                }
        }
    }

    fun deleteWorkDay(day: String): Job = viewModelScope.launch {
        _inProgress.value = true
        deleteWorkDayUseCase(day).also { resource ->
            _deleteWorkDayRes.value = resource
            _inProgress.value = false
        }
    }
}