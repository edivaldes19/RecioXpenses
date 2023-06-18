package com.edival.recioxpenses.ui.views.record.adapters

import androidx.recyclerview.widget.DiffUtil
import com.edival.recioxpenses.data.model.WorkDay
import javax.inject.Inject

class WorkDayDiff @Inject constructor() : DiffUtil.ItemCallback<WorkDay>() {
    override fun areItemsTheSame(oldItem: WorkDay, newItem: WorkDay) = oldItem.uid == newItem.uid
    override fun areContentsTheSame(oldItem: WorkDay, newItem: WorkDay) = oldItem == newItem
}