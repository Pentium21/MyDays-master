package com.adelvanchik.mydays.Domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Training(
    val data: Int,
    val day: Short,
    val month: Short,
    val year: Short,
    var training: String = DEFAULT_STRING_VALUE,
) : Parcelable {
    companion object {
        const val DEFAULT_STRING_VALUE: String = "";
    }
}
