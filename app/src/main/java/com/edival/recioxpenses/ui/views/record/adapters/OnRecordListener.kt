package com.edival.recioxpenses.ui.views.record.adapters

interface OnRecordListener {
    fun onClick(day: String)
    fun onDelete(day: String)
}