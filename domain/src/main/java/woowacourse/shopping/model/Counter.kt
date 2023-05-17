package woowacourse.shopping.model

data class Counter(val value: Int) {
    operator fun plus(number: Int): Counter = Counter(value + number)

    operator fun minus(number: Int): Counter {
        val result = value - number
        if (result <= 0) return Counter(value)
        return Counter(result)
    }
}
