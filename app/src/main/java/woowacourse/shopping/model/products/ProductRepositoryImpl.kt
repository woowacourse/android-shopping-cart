package woowacourse.shopping.model.products

class ProductRepositoryImpl : ProductRepository {
    override val dummyProducts: List<Product> = dummyProductsData

    override fun fetchProducts(
        offset: Int,
        limit: Int,
    ): List<Product> {
        val end = (offset + limit).coerceAtMost(dummyProducts.size)
        return dummyProducts.subList(offset, end)
    }
}
