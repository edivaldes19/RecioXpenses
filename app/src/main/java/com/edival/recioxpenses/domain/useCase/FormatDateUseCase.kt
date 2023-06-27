package com.edival.recioxpenses.domain.useCase

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class FormatDateUseCase @Inject constructor() {
    private val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    operator fun invoke(date: Date): String = formatter.format(date)
}