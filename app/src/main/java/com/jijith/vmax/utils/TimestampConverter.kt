package com.jijith.vmax.utils

import android.arch.persistence.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*


class TimestampConverter {
    val dateFormat = SimpleDateFormat(Constants.TIME_STAM_FORMAT, Locale.getDefault());

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return Date(value!!)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromTimestamp(value: String?): Date? {
        return dateFormat.parse(value)
    }

    @TypeConverter
    fun dateToTime(date: Date?): String? {
        return dateFormat.format(date)
    }
}