package woowacourse.shopping.repository

import woowacourse.shopping.data.DummyProducts
import woowacourse.shopping.model.products.Product

class ProductRepositoryImpl(
    private val storage: DummyProducts,
) : ProductRepository {
    override fun getAllProducts(): List<Product> = storage.getAllProducts()

    override fun getSize(): Int = storage.dummyProducts.size

    override fun getSinglePage(
        start: Int,
        end: Int,
    ): List<Product> = storage.dummyProducts.subList(start, end)
}
