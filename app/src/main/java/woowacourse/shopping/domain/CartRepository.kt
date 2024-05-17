package woowacourse.shopping.domain

interface CartRepository {
    fun addData(product: Product): Result<Long>

    fun delete(product: Product): Result<Long>

    fun load(
        startPage: Int,
        pageSize: Int,
    ): Result<List<Cart>>

    fun getMaxPage(pageSize: Int): Result<Int>
}
