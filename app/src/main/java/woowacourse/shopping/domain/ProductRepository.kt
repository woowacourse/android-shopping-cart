package woowacourse.shopping.domain

interface ProductRepository {
    fun load(
        startPage: Int,
        pageSize: Int,
    ): Result<List<Product>>

    fun loadById(id: Long): Result<Product>
}
