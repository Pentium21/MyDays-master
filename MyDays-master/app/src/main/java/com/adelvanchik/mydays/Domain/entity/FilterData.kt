package com.adelvanchik.mydays.Domain.entity

data class FilterData(
    val monthStart: Int = DEFAULT_MONTH_START,
    val monthEnd: Int = DEFAULT_MONTH_END,
    val yearStart: Int = DEFAULT_YEAR_START,
    val yearEnd: Int = DEFAULT_YEAR_END,
) {
    companion object {
        const val DEFAULT_MONTH_START = 0
        const val DEFAULT_MONTH_END = 11
        const val DEFAULT_YEAR_START = 0
        const val DEFAULT_YEAR_END = 3000
    }
}