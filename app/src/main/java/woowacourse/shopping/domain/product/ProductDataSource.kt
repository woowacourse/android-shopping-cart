package woowacourse.shopping.domain.product

interface ProductDataSource {
    fun findById(
        productId: Long,
    ): Result<Product?>

    fun findInRange(
        limit: Int,
        offset: Int,
    ): Result<List<Product>>

    fun insertAll(
        vararg products: Product,
    ): Result<Unit>
}