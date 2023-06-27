package com.edival.recioxpenses.domain.useCase

import com.edival.recioxpenses.data.model.WorkDay
import com.edival.recioxpenses.domain.model.Resource
import com.edival.recioxpenses.domain.repository.WorkDayRepository
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import javax.inject.Inject

class GetWorkDayByDayUseCase @Inject constructor(
    private val workDayRepository: WorkDayRepository,
    formatDateUseCase: FormatDateUseCase
) {
    private val today = Calendar.getInstance().time
    private val todayDate = formatDateUseCase(today)
    operator fun invoke(): Flow<Resource<WorkDay>> = workDayRepository.getWorkDayByToday(todayDate)
}