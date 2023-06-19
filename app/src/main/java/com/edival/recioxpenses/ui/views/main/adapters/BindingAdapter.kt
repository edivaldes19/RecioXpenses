package com.edival.recioxpenses.ui.views.main.adapters

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.edival.recioxpenses.R

@BindingAdapter("statusTodayImage")
fun bindImageStatus(view: ImageView, status: Int) {
    view.setImageResource(
            when (status) {
                1, 2 -> R.drawable.ic_access_time_filled
                3 -> R.drawable.ic_check_circle
                else -> R.drawable.ic_history_toggle_off
            }
    )
}

@BindingAdapter("statusTodayMsg")
fun bindMsgStatus(view: TextView, status: Int) {
    view.setText(
            when (status) {
                1, 2 -> R.string.today_status_incomplete
                3 -> R.string.today_status_complete
                else -> R.string.today_status_new
            }
    )
}

@BindingAdapter("inProgressVisibility")
fun bindSetVisibility(view: View, inProgress: Boolean) {
    view.visibility = if (inProgress) View.VISIBLE else View.GONE
}

@BindingAdapter("textDecimal")
fun bindTextDecimal(view: EditText, value: Double) {
    view.setText(if (value == 0.0) "" else value.toString())
}