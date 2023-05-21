package woowacourse.shopping.utils

interface OffsetInterface {
    var offset: Int
    fun plus(value: Int): OffsetInterface
    fun minus(value: Int): OffsetInterface
    fun getOffset(): Int
}
