package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.db.CartDao
import woowacourse.shopping.data.db.CartEntity
import woowacourse.shopping.data.util.runCatchingInThread
import woowacourse.shopping.domain.model.PageableItem

class CartDataSourceImpl(
    private val cartDao: CartDao,
) : CartDataSource {
    override fun loadCartItems(
        offset: Int,
        limit: Int,
        onResult: (Result<PageableItem<CartEntity>>) -> Unit,
    ) {
        runCatchingInThread(
            block = {
                val products = cartDao.getCartItemPaged(limit, offset)
                PageableItem(products, products.isHasMore())
            },
            callback = onResult,
        )
    }

    override fun addCartItem(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        runCatchingInThread(
            block = {
                val entity = CartEntity(productId = productId)
                cartDao.insertOrUpdate(entity)
            },
            callback = onResult,
        )
    }

    override fun decreaseCartItemQuantity(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        runCatchingInThread(
            block = {
                cartDao.decreaseQuantity(productId)
            },
            callback = onResult,
        )
    }

    override fun deleteCartItem(
        productId: Long,
        onResult: (Result<Unit>) -> Unit,
    ) {
        runCatchingInThread(
            block = { cartDao.delete(productId) },
            callback = onResult,
        )
    }

    override fun findQuantityByProductId(
        productId: Long,
        onResult: (Result<Int>) -> Unit,
    ) {
        runCatchingInThread(
            block = { cartDao.findQuantityByProductId(productId) },
            callback = onResult,
        )
    }

    private fun List<CartEntity>.isHasMore(): Boolean {
        val lastCreatedAt = this.lastOrNull()?.createdAt
        return lastCreatedAt != null && cartDao.existsItemCreatedAfter(lastCreatedAt)
    }
}
