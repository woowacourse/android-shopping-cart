package woowacourse.shopping.domain.product

interface ProductRepository {
    fun fetchInRange(
        limit: Int,
        offset: Int,
        onResult: (List<Product>) -> Unit,
    )

    fun fetchById(
        id: Long,
        onResult: (Product) -> Unit,
    )

    fun insertAll(vararg products: Product)
}
