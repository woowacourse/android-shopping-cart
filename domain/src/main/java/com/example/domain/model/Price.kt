package com.example.domain.model

@JvmInline
value class Price(val value: Int) {
    init {
        require(value >= PRICE_LOWER_LIMIT) { ERROR_PRICE_RANGE }
    }

    operator fun times(other: Int): Price {
        return Price(value * other)
    }

    operator fun plus(other: Price): Price {
        return Price(value + other.value)
    }

    companion object {
        private const val PRICE_LOWER_LIMIT = 0
        private const val ERROR_PRICE_RANGE = "[ERROR] 가격은 0원 이상이어야 합니다"
    }
}
