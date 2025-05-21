package woowacourse.shopping.data.productsRepository

import woowacourse.shopping.data.storage.ProductStorage
import woowacourse.shopping.domain.Product

class ProductRepositoryImpl private constructor(
    private val storage: ProductStorage,
) : ProductRepository {
    override fun getProducts(): List<Product> = storage.getProducts()

    override fun getProducts(limit: Int): List<Product> = storage.getProducts(limit)

    override fun getProductsSize(): Int = storage.getProductsSize()

    companion object {
        private var instance: ProductRepositoryImpl? = null

        fun initialize(storage: ProductStorage): ProductRepositoryImpl = instance ?: ProductRepositoryImpl(storage).also { instance = it }
    }
}
