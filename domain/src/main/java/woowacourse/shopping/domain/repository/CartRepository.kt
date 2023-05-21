package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartProduct

interface CartRepository {
    fun findAll(): List<CartProduct>
    fun find(id: Int): CartProduct?
    fun add(id: Int, count: Int)
    fun update(id: Int, count: Int)
    fun remove(id: Int)
    fun findRange(mark: Int, rangeSize: Int): List<CartProduct>
    fun isExistByMark(mark: Int): Boolean
}
