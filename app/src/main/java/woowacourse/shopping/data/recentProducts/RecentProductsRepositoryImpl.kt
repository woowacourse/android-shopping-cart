package woowacourse.shopping.data.recentProducts

import woowacourse.shopping.data.cart.toEntity
import woowacourse.shopping.data.cart.toProduct
import woowacourse.shopping.model.product.Product

class RecentProductsRepositoryImpl(
    private val recentProductDao: RecentProductDao,
) : RecentProductsRepository {
    override fun getAll(callback: (List<Product>) -> Unit) {
        Thread {
            val values = recentProductDao.getAll().map { it.toProduct() }
            callback(values)
        }.start()
    }

    override fun add(product: Product) {
        Thread {
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
        }.start()
    }

    override fun remove(
        productId: Long,
        callback: () -> Unit,
    ) {
        Thread {
        }.start()
    }

    override fun getSecondMostRecentProduct(callback: (Product) -> Unit) {
        Thread {
            val value = recentProductDao.getSecondMostRecentProduct()
            if (value != null) callback(value.toProduct())
        }.start()
    }

    override fun getMostRecentProduct(callback: (Product) -> Unit) {
        Thread {
            val value = recentProductDao.getMostRecentProduct()
            if (value != null) callback(value.toProduct())
        }.start()
    }

    override fun update(productId: Long) {
        Thread {
            recentProductDao.updateViewedTime(productId)
        }.start()
    }

    override fun findRecentProductById(
        productId: Long,
        callback: (Product) -> Unit,
    ) {
        Thread {
            val value = recentProductDao.findRecentProductById(productId)
            if (value != null) callback(value.toProduct())
        }
    }

    companion object {
        private const val MAX_RECENT_PRODUCT_COUNT = 10
    }
}
