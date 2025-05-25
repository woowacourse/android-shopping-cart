package woowacourse.shopping.data.cart.local

import woowacourse.shopping.data.cart.CartProductEntity

class CartProductLocalDataSource(
    private val dao: CartProductDao,
) {
    fun count(): Int = dao.count()

    fun getPaged(
        limit: Int,
        offset: Int,
    ): List<CartProductEntity> = dao.getPaged(limit, offset)

    fun getQuantityByProductId(productId: Long): Int? = dao.getQuantityByProductId(productId)

    fun getTotalQuantity(): Int = dao.getTotalQuantity()

    fun updateQuantity(
        productId: Long,
        quantity: Int,
    ) = dao.updateQuantity(productId, quantity)

    fun insert(product: CartProductEntity) = dao.insert(product)

    fun deleteByProductId(productId: Long) = dao.deleteByProductId(productId)
}
