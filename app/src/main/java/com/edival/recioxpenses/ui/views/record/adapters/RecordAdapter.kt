package com.edival.recioxpenses.ui.views.record.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.edival.recioxpenses.BR
import com.edival.recioxpenses.R
import com.edival.recioxpenses.data.model.WorkDay
import com.edival.recioxpenses.databinding.ItemRecordBinding
import com.edival.recioxpenses.domain.useCase.FormatDateUseCase
import javax.inject.Inject

class RecordAdapter @Inject constructor(
    private val formatDateUseCase: FormatDateUseCase,
    diff: WorkDayDiff
) : ListAdapter<WorkDay, RecyclerView.ViewHolder>(diff) {
    private lateinit var listener: OnRecordListener
    fun setOnClickListener(listener: OnRecordListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_record, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val workDay = getItem(position)
        val date = workDay.date?.let { timestamp -> formatDateUseCase(timestamp.toDate()) }
        with(holder as RecordViewHolder) {
            setListener(workDay)
            binding?.let { view ->
                view.setVariable(BR.date, date)
                view.setVariable(BR.workDay, workDay)
                view.executePendingBindings()
            }
        }
    }

    inner class RecordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ItemRecordBinding>(view)
        fun setListener(workDay: WorkDay) {
            binding?.let { item ->
                item.root.setOnClickListener { listener.onClick(workDay.idWorkDay) }
                item.root.setOnLongClickListener {
                    listener.onDelete(workDay.idWorkDay)
                    true
                }
            }
        }
    }
}