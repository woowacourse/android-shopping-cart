package woowacourse.shopping.domain

interface CartRepository {
    fun updateQuantity(
        product: Product,
        quantityDelta: Int,
    ): Result<Long>

    fun deleteProduct(product: Product): Result<Long>

    fun load(
        startPage: Int,
        pageSize: Int,
    ): Result<List<Cart>>

    fun loadAll(): Result<List<Cart>>

    fun getMaxPage(pageSize: Int): Result<Int>
}
