package com.example.tokenlab.extensions

import com.example.tokenlab.constants.Constants
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


fun String.convertToValidDateFormat(): String {
    val localeBr = Locale("pt", "BR")
    val dateFormatDayMonthYear = "dd/MM/yyyy"
    val dateFormatYearMonthDay = "yyyy-MM-dd"


    val format = SimpleDateFormat(dateFormatYearMonthDay, localeBr)
    val date: Date = format.parse(this) as Date

    val dateFormat: DateFormat = SimpleDateFormat(dateFormatDayMonthYear, localeBr)
    return dateFormat.format(date)
}

fun String?.convertIfIsNullOrBlank(): String {
    return if (this.isNullOrBlank())
        Constants.NULL_STRING_RESPONSE
    else this
}
