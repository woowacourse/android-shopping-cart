package woowacourse.shopping

class Counter(val value: Int = DEFAULT_COUNT) {
    operator fun plus(number: Int): Counter = Counter(value + number)

    operator fun minus(number: Int): Counter {
        val result = value - number
        if (result < 0) return Counter(value)
        return Counter(result)
    }

    fun set(number: Int): Counter {
        return Counter(number)
    }

    companion object {
        private const val DEFAULT_COUNT = 1
    }
}
