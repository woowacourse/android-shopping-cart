package model

@JvmInline
value class Page(val value: Int = 1) : Comparable<Page> {

    fun next(): Page {
        return Page(value + 1)
    }

    fun prev(): Page {
        if (value == 1) {
            return Page(1)
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
