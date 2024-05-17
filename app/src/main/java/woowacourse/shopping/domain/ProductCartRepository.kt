package woowacourse.shopping.domain

interface ProductCartRepository {
    fun save(product: Product): Result<Long>

    fun delete(product: Product): Result<Long>

    fun deleteAll(): Result<Boolean>

    fun findByPaging(
        pageOffset: Int,
        pageSize: Int,
    ): Result<List<Cart>>

    fun getMaxOffset(): Result<Int>
}
