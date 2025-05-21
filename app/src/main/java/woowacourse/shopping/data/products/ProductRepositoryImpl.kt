package woowacourse.shopping.data.products

import woowacourse.shopping.model.product.Product

class ProductRepositoryImpl : ProductRepository {
    private val dummyProducts: List<Product> = dummyProductsData

    override fun getAll(): List<Product> = dummyProducts

    override fun fetchProducts(
        offset: Int,
        limit: Int,
    ): List<Product> {
        val end = (offset + limit).coerceAtMost(dummyProducts.size)
        return dummyProducts.subList(offset, end)
    }
}
