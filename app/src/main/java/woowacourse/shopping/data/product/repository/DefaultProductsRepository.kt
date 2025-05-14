package woowacourse.shopping.data.product.repository

import woowacourse.shopping.data.product.entity.ProductEntity
import woowacourse.shopping.data.product.storage.ProductsStorage
import woowacourse.shopping.data.product.storage.VolatileProductsStorage
import woowacourse.shopping.domain.product.Product

class DefaultProductsRepository(
    private val productsStorage: ProductsStorage = VolatileProductsStorage
) : ProductsRepository {
    override val products: List<Product>
        get() = productsStorage.products.map(ProductEntity::toDomain)
}
