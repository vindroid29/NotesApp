package com.globant.notesapp.playground

import java.util.Calendar
import java.util.Calendar.DAY_OF_WEEK
import java.util.Locale


fun main(){

    val daysArray = arrayOf("Tuesday", "Friday", "Monday", "Thursday")
    val cal = Calendar.getInstance()
    println(cal.getDisplayName(DAY_OF_WEEK,0, Locale.getDefault()))

}