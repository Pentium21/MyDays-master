package com.adelvanchik.mydays.Domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Parameters(
    val data: Int,
    val day: Short,
    val month: Short,
    val year: Short,
    val weight: Int = DEFAULT_VALUE,
    val height: Int = DEFAULT_VALUE,

) : Parcelable {
    companion object {
        const val DEFAULT_VALUE = -7;
    }
}
