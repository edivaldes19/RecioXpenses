package com.edival.recioxpenses.domain.useCase

import com.edival.recioxpenses.data.model.WorkDay
import com.edival.recioxpenses.domain.model.Resource
import com.edival.recioxpenses.domain.repository.WorkDayRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEverydayWorkUseCase @Inject constructor(private val workDayRepository: WorkDayRepository) {
    operator fun invoke(): Flow<Resource<List<WorkDay>>> = workDayRepository.getEverydayWork()
}