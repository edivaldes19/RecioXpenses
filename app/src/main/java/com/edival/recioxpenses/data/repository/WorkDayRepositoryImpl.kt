package com.edival.recioxpenses.data.repository

import com.edival.recioxpenses.data.model.WorkDay
import com.edival.recioxpenses.domain.model.Resource
import com.edival.recioxpenses.domain.repository.WorkDayRepository
import com.edival.recioxpenses.ui.utils.Constants
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WorkDayRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) : WorkDayRepository {
    override val daysCollection: CollectionReference get() = db.collection(Constants.COLL_DAYS)
    override fun getEverydayWork(): Flow<Resource<List<WorkDay>>> {
        return daysCollection.snapshots().mapNotNull { snapshot ->
            snapshot.mapNotNull { documentSnapshot ->
                documentSnapshot.toObject<WorkDay>().apply { idWorkDay = documentSnapshot.id }
            }.let { workDayList -> Resource.Success(workDayList) }
        }
    }

    override fun getWorkDayByDay(today: String): Flow<Resource<WorkDay>> {
        return daysCollection.document(today).snapshots().mapNotNull { snapshot ->
            if (snapshot.exists()) {
                Resource.Success(snapshot.toObject<WorkDay>()!!.apply { idWorkDay = snapshot.id })
            } else Resource.Error()
        }
    }

    override suspend fun saveWorkDay(day: String, startCapital: Double?, finalCapital: Double?, expenses: Double?, workDay: WorkDay): Resource<Unit> {
        return try {
            startCapital?.let { workDay.startCapital = it }
            finalCapital?.let { workDay.finalCapital = it }
            expenses?.let { workDay.expenses = it }
            daysCollection.document(day).set(workDay).await()
            Resource.Success(Unit)
        } catch (e: FirebaseFirestoreException) {
            Resource.Error(e.message)
        }
    }

    override suspend fun deleteWorkDay(day: String): Resource<Unit> {
        return try {
            daysCollection.document(day).delete().await()
            Resource.Success(Unit)
        } catch (e: FirebaseFirestoreException) {
            Resource.Error(e.message)
        }
    }
}