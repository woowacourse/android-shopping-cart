package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.dao.CartDao
import woowacourse.shopping.data.db.entity.CartEntity

class CartDataSource(
    private val dao: CartDao,
) {
    fun getCartByProductId(productId: Long): CartEntity? = dao.cartByProductId(productId)

    fun getCartsByProductIds(productIds: List<Long>): List<CartEntity?> {
        return dao.cartsByProductIds(productIds)
    }

    fun upsert(entity: CartEntity) = dao.upsert(entity)

    fun deleteCartByProductId(productId: Long) = dao.deleteByProductId(productId)

    fun singlePage(
        page: Int,
        size: Int,
    ): List<CartEntity> = dao.cartSinglePage(page, size)

    fun pageCount(size: Int): Int = dao.pageCount(size)
}
