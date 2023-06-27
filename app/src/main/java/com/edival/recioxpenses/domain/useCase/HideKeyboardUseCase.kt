package com.edival.recioxpenses.domain.useCase

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class HideKeyboardUseCase @Inject constructor(@ApplicationContext private val context: Context) {
    operator fun invoke(view: View) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}