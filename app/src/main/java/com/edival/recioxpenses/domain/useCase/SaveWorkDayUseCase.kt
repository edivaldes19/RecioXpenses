package com.edival.recioxpenses.domain.useCase

import com.edival.recioxpenses.data.model.WorkDay
import com.edival.recioxpenses.domain.model.Resource
import com.edival.recioxpenses.domain.repository.WorkDayRepository
import java.util.Calendar
import javax.inject.Inject

class SaveWorkDayUseCase @Inject constructor(
    private val workDayRepository: WorkDayRepository,
    formatDateUseCase: FormatDateUseCase
) {
    private val today = Calendar.getInstance().time
    private val todayDate = formatDateUseCase(today)
    suspend operator fun invoke(
        startCapital: Double?,
        finalCapital: Double?,
        expenses: Double?,
        workDay: WorkDay
    ): Resource<Unit> =
        workDayRepository.saveWorkDay(todayDate, startCapital, finalCapital, expenses, workDay)
}