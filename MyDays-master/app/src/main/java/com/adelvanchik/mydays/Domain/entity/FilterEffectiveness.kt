package com.adelvanchik.mydays.Domain.entity

data class FilterEffectiveness(
    val isIncludeEffectivenessEmpty: Boolean = DEFAULT_BOOLEAN_VALUE,
    val isIncludeEffectiveness1: Boolean = DEFAULT_BOOLEAN_VALUE,
    val isIncludeEffectiveness2: Boolean = DEFAULT_BOOLEAN_VALUE,
    val isIncludeEffectiveness3: Boolean = DEFAULT_BOOLEAN_VALUE,
) {
    companion object {
        private const val DEFAULT_BOOLEAN_VALUE = true
    }
}
