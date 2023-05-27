package woowacourse.shopping

class Counter(val value: Int = DEFAULT_COUNT, val minimumCount: Int = DEFAULT_COUNT) {

    init {
        require(value >= minimumCount) { "최소한 개수는 $minimumCount 보다 크거나 같아야 합니다" }
    }

    operator fun plus(number: Int): Counter = Counter(value + number, minimumCount)

    operator fun minus(number: Int): Counter {
        val result = value - number
        if (result >= minimumCount) {
            return Counter(value - number, minimumCount)
        }
        return Counter(value, minimumCount)
    }

    fun set(number: Int): Counter {
        return Counter(number, minimumCount)
    }

    companion object {
        private const val DEFAULT_COUNT = 1
    }
}
