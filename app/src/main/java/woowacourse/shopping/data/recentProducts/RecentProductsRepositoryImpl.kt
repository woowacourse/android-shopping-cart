package woowacourse.shopping.data.recentProducts

import woowacourse.shopping.data.cart.toCartItem
import woowacourse.shopping.data.cart.toRecentProductEntity
import woowacourse.shopping.data.runAsyncResult
import woowacourse.shopping.model.cart.CartItem

class RecentProductsRepositoryImpl(
    private val recentProductDao: RecentProductDao,
) : RecentProductsRepository {
    override fun getAll(callback: (Result<List<CartItem>>) -> Unit) {
        runAsyncResult(
            function = {
                recentProductDao.getAll().map { it.toCartItem() }
            },
            callback,
        )
    }

    override fun add(
        cartItem: CartItem,
        callback: (Result<Unit>) -> Unit,
    ) {
        runAsyncResult(function = {
            val existing: RecentProductEntity? =
                recentProductDao.findRecentProductById(cartItem.product.id)
            if (existing == null) {
                val allProductsSize: Int = recentProductDao.getAllSize()
                deleteOldestProduct(allProductsSize)
                recentProductDao.insert(cartItem.toRecentProductEntity())
            } else {
                recentProductDao.updateViewedTime(cartItem.product.id)
                recentProductDao.updateQuantity(cartItem.product.id, cartItem.quantity)
            }
        }, callback)
    }

    override fun remove(
        productId: Long,
        callback: (Result<Unit>) -> Unit,
    ) {
        runAsyncResult(function = { recentProductDao.delete(productId) }, callback)
    }

    override fun getSecondMostRecentProduct(callback: (Result<CartItem>) -> Unit) {
        runAsyncResult(
            function = { recentProductDao.getSecondMostRecentProduct()?.toCartItem() },
            callback,
        )
    }

    override fun update(
        productId: Long,
        callback: (Result<Unit>) -> Unit,
    ) {
        runAsyncResult(function = { recentProductDao.updateViewedTime(productId) }, callback)
    }

    override fun findRecentProductById(
        productId: Long,
        callback: (Result<CartItem>) -> Unit,
    ) {
        runAsyncResult(function = {
            recentProductDao.findRecentProductById(productId)?.toCartItem()
        }, callback)
    }

    private fun deleteOldestProduct(allProductsSize: Int) {
        if (allProductsSize >= MAX_RECENT_PRODUCT_COUNT) {
            val oldest: RecentProductEntity? = recentProductDao.getOldest()
            if (oldest != null) {
                recentProductDao.delete(oldest.id)
            }
        }
    }

    companion object {
        private const val MAX_RECENT_PRODUCT_COUNT = 10
    }
}
