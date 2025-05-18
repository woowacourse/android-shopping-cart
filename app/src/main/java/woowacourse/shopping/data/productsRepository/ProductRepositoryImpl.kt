package woowacourse.shopping.data.productsRepository

import woowacourse.shopping.domain.Product

class ProductRepositoryImpl private constructor() : ProductRepository {
    private var currentIndex = 0

    override fun getProducts(): List<Product> = DummyProducts.value

    override fun getProducts(limit: Int): List<Product> {
        val products = DummyProducts.value.drop(currentIndex).take(limit)
        currentIndex += limit
        return products
    }

    companion object {
        private var instance: ProductRepositoryImpl? = null

        fun initialize(): ProductRepositoryImpl = instance ?: ProductRepositoryImpl().also { instance = it }
    }
}
