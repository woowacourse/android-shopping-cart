package woowacourse.shopping.data.repository.products.catalog

import woowacourse.shopping.data.source.products.catalog.ProductStorage
import woowacourse.shopping.domain.Product

class ProductRepositoryImpl private constructor(
    private val storage: ProductStorage,
) : ProductRepository {
    override fun getProducts(): List<Product> = storage.getProducts()

    override fun getProducts(
        currentIndex: Int,
        limit: Int,
    ): List<Product> = storage.getProducts(currentIndex, limit)

    override fun getProductsSize(): Int = storage.getProductsSize()

    companion object {
        private var instance: ProductRepositoryImpl? = null

        @Synchronized
        fun initialize(storage: ProductStorage): ProductRepository = instance ?: ProductRepositoryImpl(storage).also { instance = it }
    }
}
