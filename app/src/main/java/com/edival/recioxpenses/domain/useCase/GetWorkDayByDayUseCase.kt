package com.edival.recioxpenses.domain.useCase

import com.edival.recioxpenses.data.model.WorkDay
import com.edival.recioxpenses.domain.model.Resource
import com.edival.recioxpenses.domain.repository.WorkDayRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWorkDayByDayUseCase @Inject constructor(private val workDayRepository: WorkDayRepository) {
    operator fun invoke(today: String): Flow<Resource<WorkDay>> = workDayRepository.getWorkDayByDay(today)
}