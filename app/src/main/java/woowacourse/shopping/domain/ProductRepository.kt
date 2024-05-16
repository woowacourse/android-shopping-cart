package woowacourse.shopping.domain

interface ProductRepository {
    fun load(
        pageOffset: Int,
        pageSize: Int,
    ): Result<List<Product>>

    fun loadById(id: Long): Result<Product>
}
