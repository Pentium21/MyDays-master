package com.adelvanchik.mydays.Domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Thoughts(
    val data: Int,
    val day: Short,
    val month: Short,
    val year: Short,
    val thoughts: String = DEFAULT_STRING_VALUE,
) : Parcelable {
    companion object {
        const val DEFAULT_STRING_VALUE: String = "";
    }
}