package woowacourse.shopping.domain

interface ProductRepository {
    fun getProducts(): List<Product>

    fun getPagedProducts(
        page: Int,
        pageSize: Int,
    ): List<Product>

    fun getCartItems(onResult: (Result<List<CartItem>>) -> Unit)

    fun insertProduct(
        product: Product,
        onResult: (Result<Unit>) -> Unit,
    )

    fun deleteProduct(
        productId: Long,
        onResult: (Result<Long>) -> Unit,
    )
}
