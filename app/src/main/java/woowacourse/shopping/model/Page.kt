package woowacourse.shopping.model

@JvmInline
value class Page(val value: Int = 0) {

    fun next(): Page {
        return Page(value + 1)
    }

    fun prev(): Page {
        if (value == 0) {
            return Page(0)
        }
        return Page(value - 1)
    }
}
