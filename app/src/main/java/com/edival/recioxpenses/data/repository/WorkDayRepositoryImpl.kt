package com.edival.recioxpenses.data.repository

import com.edival.recioxpenses.data.model.WorkDay
import com.edival.recioxpenses.domain.model.Resource
import com.edival.recioxpenses.domain.repository.WorkDayRepository
import com.edival.recioxpenses.ui.utils.Constants
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class WorkDayRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) : WorkDayRepository {
    override val daysCollection: CollectionReference get() = db.collection(Constants.COLL_DAYS)
    override fun getEverydayWork(): Flow<Resource<List<WorkDay>>> = callbackFlow {
        val registration = daysCollection.addSnapshotListener { value, e ->
            if (e != null) {
                trySend(Resource.Error(e.message))
                close()
            }
            val workDayList = value?.mapNotNull { snapshot ->
                snapshot.toObject<WorkDay>().apply { uid = snapshot.id }
            }.orEmpty()
            trySend(Resource.Success(workDayList))
        }
        awaitClose { registration.remove() }
    }

    override fun getWorkDayByDay(today: String): Flow<Resource<WorkDay>> = callbackFlow {
        val registration = daysCollection.document(today).addSnapshotListener { snapshot, e ->
            if (e != null) {
                trySend(Resource.Error(e.message))
                close()
            }
            if (snapshot != null && snapshot.exists()) {
                trySend(Resource.Success(snapshot.toObject<WorkDay>()!!.apply { uid = snapshot.id }))
            } else {
                trySend(Resource.Error(e?.message))
                close()
            }
        }
        awaitClose { registration.remove() }
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
}