package woowacourse.shopping.data.recentProducts

import woowacourse.shopping.data.cart.toEntity
import woowacourse.shopping.data.cart.toProduct
import woowacourse.shopping.data.runAsyncResult
import woowacourse.shopping.model.product.Product

class RecentProductsRepositoryImpl(
    private val recentProductDao: RecentProductDao,
) : RecentProductsRepository {
    override fun getAll(callback: (Result<List<Product>>) -> Unit) {
        runAsyncResult(
            function = { recentProductDao.getAll().map { it.toProduct() } },
            callback,
        )
    }

    override fun add(
        product: Product,
        callback: (Result<Unit>) -> Unit,
    ) {
        runAsyncResult(function = {
            val existing = recentProductDao.findRecentProductById(product.id)
            if (existing == null) {
                val allProducts = recentProductDao.getAll()
                if (allProducts.size >= MAX_RECENT_PRODUCT_COUNT) {
                    val oldest = allProducts.lastOrNull()
                    if (oldest != null) {
                        recentProductDao.delete(oldest.id)
                    }
                }
                recentProductDao.insert(product.toEntity())
            } else {
                recentProductDao.updateViewedTime(product.id)
            }
        }, callback)
    }

    override fun remove(
        productId: Long,
        callback: (Result<Unit>) -> Unit,
    ) {
        runAsyncResult(function = { recentProductDao.delete(productId) }, callback)
    }

    override fun getSecondMostRecentProduct(callback: (Result<Product>) -> Unit) {
        runAsyncResult(
            function = { recentProductDao.getSecondMostRecentProduct()?.toProduct() },
            callback,
        )
    }

    override fun getMostRecentProduct(callback: (Result<Product>) -> Unit) {
        runAsyncResult(
            function = { recentProductDao.getMostRecentProduct()?.toProduct() },
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
        callback: (Result<Product>) -> Unit,
    ) {
        runAsyncResult(function = {
            recentProductDao.findRecentProductById(productId)?.toProduct()
        }, callback)
    }

    companion object {
        private const val MAX_RECENT_PRODUCT_COUNT = 10
    }
}
