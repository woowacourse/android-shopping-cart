package woowacourse.shopping.domain

interface ProductRepository {
    fun findByPaging(
        pageOffset: Int,
        pageSize: Int,
    ): Result<List<Product>>

    fun findById(id: Long): Result<Product>
}
