package woowacourse.shopping.domain

interface CartRepository {
    fun addData(product: Product): Result<Long>

    fun delete(product: Product): Result<Long>

    fun load(
        pageOffset: Int,
        pageSize: Int,
    ): Result<List<Cart>>

    fun getMaxOffset(pageSize: Int): Result<Int>
}
