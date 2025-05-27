package woowacourse.shopping.data.datasource.local

import woowacourse.shopping.data.dao.CartProductDao
import woowacourse.shopping.data.entity.CartProductEntity

class CartProductLocalDataSource(
    private val dao: CartProductDao,
) {
    fun insert(cartProductEntity: CartProductEntity) = dao.insert(cartProductEntity)

    fun getTotalCount(): Int = dao.getTotalCount()

    fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): List<CartProductEntity> = dao.getPagedProducts(limit, offset)

    fun getQuantityByProductId(productId: Long): Int? = dao.getQuantityByProductId(productId)

    fun getTotalQuantity(): Int = dao.getTotalQuantity()

    fun updateQuantity(
        productId: Long,
        quantity: Int,
    ) = dao.updateQuantity(productId, quantity)

    fun deleteByProductId(productId: Long) = dao.deleteByProductId(productId)
}
