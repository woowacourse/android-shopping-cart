package com.shopping.domain

@JvmInline
value class Price(val value: Int) {
    init {
        require(value >= MIN_PRICE) { "[잘못된 값: $value] 가격은 $MIN_PRICE 이상 이어야 합니다." }
    }

    operator fun times(times: Int): Price {
        return Price(value * times)
    }

    operator fun plus(other: Price): Price {
        return Price(value + other.value)
    }

    companion object {
        private const val MIN_PRICE = 0
    }
}
