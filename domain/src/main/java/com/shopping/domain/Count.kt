package com.shopping.domain

data class Count(val value: Int) {
    init {
        require(value >= MINIMUM_COUNT) { COUNT_VALUE_ERROR }
    }

    fun inc() = Count(value + 1)

    fun dec() = Count(maxOf(MINIMUM_COUNT, value - 1))

    companion object {
        private const val MINIMUM_COUNT = 0
        private const val COUNT_VALUE_ERROR = "count는 양수여야 합니다."
    }
}
