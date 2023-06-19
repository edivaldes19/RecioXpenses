package com.edival.recioxpenses.domain.useCase

import com.edival.recioxpenses.domain.model.Resource
import com.edival.recioxpenses.domain.repository.WorkDayRepository
import javax.inject.Inject

class DeleteWorkDayUseCase @Inject constructor(private val workDayRepository: WorkDayRepository) {
    suspend operator fun invoke(day: String): Resource<Unit> = workDayRepository.deleteWorkDay(day)
}