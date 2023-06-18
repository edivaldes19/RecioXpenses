package com.edival.recioxpenses.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp

data class WorkDay(
        @get:Exclude var uid: String = "",
        @ServerTimestamp var date: Timestamp? = null,
        var startCapital: Double = 0.0,
        var finalCapital: Double = 0.0,
        var expenses: Double = 0.0
) {
    @Exclude
    fun getStatus(): Int {
        var status = 0
        if (startCapital > 0) status++
        if (finalCapital > 0) status++
        if (expenses > 0) status++
        return status
    }
}