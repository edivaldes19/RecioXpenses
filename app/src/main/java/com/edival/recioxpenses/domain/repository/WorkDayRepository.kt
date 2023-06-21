package com.edival.recioxpenses.domain.repository

import com.edival.recioxpenses.data.model.WorkDay
import com.edival.recioxpenses.domain.model.Resource
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.flow.Flow

interface WorkDayRepository {
    val daysCollection: CollectionReference
    fun getEverydayWork(): Flow<Resource<List<WorkDay>>>
    fun getWorkDayByDay(today: String): Flow<Resource<WorkDay>>
    suspend fun saveWorkDay(
        day: String,
        startCapital: Double?,
        finalCapital: Double?,
        expenses: Double?,
        workDay: WorkDay
    ): Resource<Unit>

    suspend fun deleteWorkDay(day: String): Resource<Unit>
}