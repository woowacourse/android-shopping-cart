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
        dao.cartByProductId(entity.productId)?.let {
            val updated = it.copy(quantity = it.quantity + entity.quantity)
            dao.update(updated)
        } ?: run {
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
