package com.adelvanchik.mydays.Domain.entity

data class MinMaxEffectiveness(
    val min: Short = MIN_VALUE,
    val max: Short = MAX_VALUE,
) {
    companion object {
        private const val MIN_VALUE: Short = Day.DEFAULT_SHORT_VALUE
        private const val MAX_VALUE: Short = Day.EFFECTIVENESS_3_END
    }
}
