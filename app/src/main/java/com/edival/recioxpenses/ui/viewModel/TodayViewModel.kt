package com.edival.recioxpenses.ui.viewModel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edival.recioxpenses.data.model.WorkDay
import com.edival.recioxpenses.domain.model.Resource
import com.edival.recioxpenses.domain.useCase.GetWorkDayByDayUseCase
import com.edival.recioxpenses.domain.useCase.HideKeyboardUseCase
import com.edival.recioxpenses.domain.useCase.SaveWorkDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodayViewModel @Inject constructor(
    private val getWorkDayByDayUseCase: GetWorkDayByDayUseCase,
    private val saveWorkDayUseCase: SaveWorkDayUseCase,
    private val hideKeyboardUseCase: HideKeyboardUseCase
) : ViewModel() {
    private val _inProgress = MutableLiveData<Boolean>()
    val inProgress: LiveData<Boolean> = _inProgress
    private val _isHideKeyboard = MutableLiveData<Boolean>()
    val isHideKeyboard: LiveData<Boolean> = _isHideKeyboard
    private val _getWorkDayByDayRes = MutableLiveData<Resource<WorkDay>>()
    val getWorkDayByDayRes: LiveData<Resource<WorkDay>> = _getWorkDayByDayRes
    private val _saveWorkDayRes = MutableLiveData<Resource<Unit>>()
    val saveWorkDayRes: LiveData<Resource<Unit>> = _saveWorkDayRes
    val currentWorkDay = MutableLiveData<WorkDay>()

    init {
        viewModelScope.launch {
            showLoadingComp(true)
            getWorkDayByDayUseCase()
                .catch { error ->
                    _getWorkDayByDayRes.value = Resource.Error(error.message)
                    showLoadingComp(false)
                }.collect { resource ->
                    _getWorkDayByDayRes.value = resource
                    showLoadingComp(false)
                }
        }
    }

    private fun showLoadingComp(isLoading: Boolean) {
        _inProgress.value = isLoading
        _isHideKeyboard.value = isLoading
    }

    fun saveWorkDay(
        startCapital: Double? = null,
        finalCapital: Double? = null,
        expenses: Double? = null
    ): Job = viewModelScope.launch {
        showLoadingComp(true)
        saveWorkDayUseCase(
            startCapital,
            finalCapital,
            expenses,
            currentWorkDay.value ?: WorkDay()
        ).also { resource ->
            _saveWorkDayRes.value = resource
            showLoadingComp(false)
        }
    }

    fun hideKeyboard(view: View) {
        hideKeyboardUseCase(view)
    }
}