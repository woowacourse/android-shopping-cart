package woowacourse.shopping.model.products

interface ProductRepository {
    val dummyProducts: List<Product>

    fun fetchProducts(
        offset: Int,
        limit: Int,
    ): List<Product>
}
