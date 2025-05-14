package woowacourse.shopping.data.product.repository

import android.util.Log
import woowacourse.shopping.data.product.entity.ProductEntity
import woowacourse.shopping.data.product.storage.ProductsStorage
import woowacourse.shopping.data.product.storage.VolatileProductsStorage
import woowacourse.shopping.domain.product.Product

class DefaultProductsRepository(
    private val productsStorage: ProductsStorage = VolatileProductsStorage,
) : ProductsRepository {
    override var loadable: Boolean = false
        private set

    override fun load(
        lastProductId: Long?,
        size: Int,
    ): List<Product> {
        val products: List<Product> =
            productsStorage.load(lastProductId, size).map(ProductEntity::toDomain)

        loadable =
            if (products.lastOrNull() == null) {
                true
            } else {
                products.last().id != productsStorage.lastProductId
            }

        return products
    }
}
