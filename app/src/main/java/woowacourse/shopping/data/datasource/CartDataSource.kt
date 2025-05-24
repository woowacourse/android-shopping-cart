package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.dao.CartDao
import woowacourse.shopping.data.db.entity.CartEntity

class CartDataSource(
    private val dao: CartDao,
) {
    fun getCartByProductId(productId: Long): CartEntity? = dao.cartByProductId(productId)

    fun getCartsByProductIds(productIds: List<Long>): List<CartEntity?> {
        return productIds.map { dao.cartByProductId(it) }
    }

    fun upsert(entity: CartEntity) = dao.upsert(entity)

    fun modify(entity: CartEntity) {
        val existing = dao.cartByProductId(entity.productId)
        if (existing != null) {
            val updated = existing.copy(quantity = existing.quantity + entity.quantity)
            dao.update(updated)
        } else {
            dao.insert(entity)
        }
    }

    fun deleteCartByProductId(productId: Long) = dao.deleteByProductId(productId)

    fun singlePage(
        page: Int,
        size: Int,
    ): List<CartEntity> = dao.cartSinglePage(page, size)

    fun pageCount(size: Int): Int = dao.pageCount(size)
}
