package com.example.newsapp.util

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

fun Date.convertToAgoTime():String{
    return DateUtils.getRelativeTimeSpanString(
        time ?: 0,
        System.currentTimeMillis(), 0
    ).toString()
}