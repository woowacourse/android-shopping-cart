package woowacourse.shopping.model

@JvmInline
value class Page(val value: Int = 0) : Comparable<Page> {

    fun next(): Page {
        return Page(value + 1)
    }

    fun prev(): Page {
        if (value == 0) {
            return Page(0)
        }
        return Page(value - 1)
    }

    override fun compareTo(other: Page): Int {

        return when {
            other.value > value -> -1
            other.value < value -> 1
            else -> 0
        }
    }
}
