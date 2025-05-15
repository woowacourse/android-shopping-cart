package woowacourse.shopping.domain.product

interface ProductRepository {
    fun productsByPageNumberAndSize(
        pageNumber: Int,
        loadSize: Int,
    ): List<Product>

    fun fetchById(id: Long): Product?

    fun canMoreLoad(
        pageNumber: Int,
        loadSize: Int,
    ): Boolean
}
