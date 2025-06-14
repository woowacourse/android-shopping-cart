package woowacourse.shopping.repository

import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.model.products.Product

class ProductRepositoryImpl(
    private val storage: DummyProducts,
) : ProductRepository {
    override fun fetchAllProducts(): List<Product> = storage.getAllProducts()

    override fun productCount(): Int = storage.dummyProducts.size

    override fun pageOfProducts(
        start: Int,
        end: Int,
    ): List<Product> = storage.dummyProducts.subList(start, end)

    override fun getProductsById(productIds: List<Int>): List<Product> = storage.dummyProducts.filter { productIds.contains(it.id) }
}
