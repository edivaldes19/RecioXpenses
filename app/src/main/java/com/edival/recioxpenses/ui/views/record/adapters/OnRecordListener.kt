package com.edival.recioxpenses.ui.views.record.adapters

import com.edival.recioxpenses.data.model.WorkDay

interface OnRecordListener {
    fun onClick(workDay: WorkDay)
}