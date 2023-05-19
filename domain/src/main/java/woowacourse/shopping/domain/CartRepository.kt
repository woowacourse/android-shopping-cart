package woowacourse.shopping.domain

interface CartRepository {
    fun findAll(): List<CartProduct>
    fun add(id: Int, count: Int, check: Boolean)
    fun remove(id: Int)
    fun findRange(mark: Int, rangeSize: Int): List<CartProduct>
    fun isExistByMark(mark: Int): Boolean
    fun plusCount(id: Int)
    fun subCount(id: Int)
    fun findCheckedItem(): List<CartProduct>
    fun updateCheckState(id: Int, checked: Boolean)
}
