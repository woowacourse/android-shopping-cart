package woowacourse.shopping.utils

interface OffsetInterface {
    fun plus(value: Int): OffsetInterface
    fun minus(value: Int): OffsetInterface
    fun getOffset(): Int
}
