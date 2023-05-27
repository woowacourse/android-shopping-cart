package woowacourse.shopping.domain

interface CartProductRepository {
    fun findAll(): List<CartProduct>
    fun add(id: Int, count: Int, check: Boolean)
    fun remove(id: Int)
    fun findRange(mark: Int, rangeSize: Int): List<CartProduct>
    fun isExistByMark(mark: Int): Boolean
    fun updatePlus(id: Int)
    fun UpdateSub(id: Int)
    fun findCheckedItem(): List<CartProduct>
    fun updateCheckState(id: Int, checked: Boolean)
}
