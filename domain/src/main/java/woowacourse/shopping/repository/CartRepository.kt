package woowacourse.shopping.repository

import woowacourse.shopping.model.CartProduct

interface CartRepository {
    fun getAll(): List<CartProduct>
    fun insert(productId: Int)
    fun getSubList(offset: Int, size: Int): List<CartProduct>
    fun remove(id: Int)
}
