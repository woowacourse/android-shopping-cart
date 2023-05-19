package model

@JvmInline
value class Price(val value: Int = 0) {

    operator fun plus(other: Price): Price = Price(value + other.value)
    operator fun minus(other: Price): Price = Price(value - other.value)
    operator fun times(count: Int): Price = Price(value * count)
}
