package woowacourse.shopping.model.data

import woowacourse.shopping.model.Cart

interface CartDao {
    fun itemSize(): Int

    fun save(cart: Cart): Long

    fun find(id: Long): Cart

    fun findAll(): List<Cart>

    fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Cart>

    fun deleteAll()

    fun delete(id: Long)
}
