package woowacourse.shopping.repository

import woowacourse.shopping.model.CartProducts
import woowacourse.shopping.model.Product

interface CartRepository {
    fun getPage(index: Int, size: Int): CartProducts
    fun hasNextPage(index: Int, size: Int): Boolean
    fun hasPrevPage(index: Int, size: Int): Boolean
    fun getTotalCount(): Int
    fun getTotalSelectedCount(): Int
    fun getTotalPrice(): Int
    fun insert(product: Product)
    fun remove(id: Int)
    fun updateCount(id: Int, count: Int): Int
    fun updateChecked(id: Int, checked: Boolean)
    fun getAll(): CartProducts
}
