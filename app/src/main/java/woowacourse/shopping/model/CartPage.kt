package woowacourse.shopping.model

import kotlin.math.min

data class CartPage(val number: Int = DEFAULT_PAGE_NUM) {
    fun plus(): CartPage {
        return CartPage(number + OFFSET)
    }

    fun minus(): CartPage {
        val changedNumber = (number - OFFSET).coerceAtLeast(1)
        return CartPage(changedNumber)
    }

    fun isNotFirst(): Boolean {
        return number != DEFAULT_PAGE_NUM
    }

    fun isNotLast(itemSize: Int): Boolean {
        val fromIndex = (number - OFFSET) * PAGE_SIZE
        val toIndex = min(fromIndex + PAGE_SIZE, itemSize)
        return toIndex != (itemSize)
    }

    companion object {
        private const val OFFSET = 1
        private const val DEFAULT_PAGE_NUM = 1
        private const val PAGE_SIZE = 5
    }
}
