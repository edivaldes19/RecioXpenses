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
import com.edival.recioxpenses.ui.utils.UtilityFunctions
import javax.inject.Inject

class RecordAdapter @Inject constructor(
        private val utils: UtilityFunctions, diff: WorkDayDiff
) : ListAdapter<WorkDay, RecyclerView.ViewHolder>(diff) {
    private lateinit var listener: OnRecordListener
    fun setOnClickListener(listener: OnRecordListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_record, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position).also { workDay ->
            with(holder as ViewHolder) {
                setListener(workDay)
                binding?.let { view ->
                    view.setVariable(BR.workDay, workDay)
                    view.setVariable(BR.utils, utils)
                    view.executePendingBindings()
                }
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ItemRecordBinding>(view)
        fun setListener(workDay: WorkDay) {
            binding?.root?.setOnClickListener { listener.onClick(workDay) }
        }
    }
}