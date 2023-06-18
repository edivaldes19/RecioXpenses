package com.edival.recioxpenses.domain.useCase

import com.edival.recioxpenses.data.model.WorkDay
import com.edival.recioxpenses.domain.model.Resource
import com.edival.recioxpenses.domain.repository.WorkDayRepository
import javax.inject.Inject

class SaveWorkDayUseCase @Inject constructor(private val workDayRepository: WorkDayRepository) {
    suspend operator fun invoke(
            day: String,
            startCapital: Double?,
            finalCapital: Double?,
            expenses: Double?,
            workDay: WorkDay
    ): Resource<Unit> {
        return workDayRepository.saveWorkDay(day, startCapital, finalCapital, expenses, workDay)
    }
}