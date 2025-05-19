package woowacourse.shopping.data.productsRepository

import woowacourse.shopping.domain.Product

class ProductRepositoryImpl private constructor(
    private val data: List<Product>,
) : ProductRepository {
    private var currentIndex = 0

    override fun getProducts(): List<Product> = data

    override fun getProducts(limit: Int): List<Product> {
        val products = data.drop(currentIndex).take(limit)
        currentIndex += limit
        return products
    }

    companion object {
        private var instance: ProductRepositoryImpl? = null

        fun initialize(data: List<Product>): ProductRepositoryImpl = instance ?: ProductRepositoryImpl(data).also { instance = it }
    }
}
