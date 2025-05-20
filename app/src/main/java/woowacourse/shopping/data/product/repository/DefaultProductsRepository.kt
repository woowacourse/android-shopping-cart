package woowacourse.shopping.data.product.repository

import woowacourse.shopping.data.product.entity.ProductEntity
import woowacourse.shopping.data.product.storage.ProductsStorage
import woowacourse.shopping.data.product.storage.VolatileProductsStorage
import woowacourse.shopping.domain.product.Product

class DefaultProductsRepository(
    private val productsStorage: ProductsStorage = VolatileProductsStorage,
) : ProductsRepository {
    override fun load(): List<Product> {
        return productsStorage.load().map(ProductEntity::toDomain)
//
//        loadable =
//            if (products.lastOrNull() == null) {
//                true
//            } else {
//                products.last().id != productsStorage.lastProductId
//            }
//
//        return products
    }
}
