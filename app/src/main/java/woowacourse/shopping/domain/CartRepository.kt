package woowacourse.shopping.domain

interface CartRepository {
    fun addData(productId: Long): Result<Long>

    fun load(
        pageOffset: Int,
        pageSize: Int,
    ): Result<List<Cart>>
}
