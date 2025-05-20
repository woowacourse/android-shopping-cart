package woowacourse.shopping.domain

interface ProductRepository {
    fun getProducts(): List<Product>

    fun getPagedProducts(
        page: Int,
        pageSize: Int,
    ): List<Product>

    fun getCartProductCount(onResult: (Result<Int>) -> Unit)

    fun getCartProducts(onResult: (Result<List<Product>>) -> Unit)

    fun getPagedCartProducts(
        limit: Int,
        page: Int,
        onResult: (Result<List<Product>>) -> Unit,
    )

    fun insertProduct(
        product: Product,
        onResult: (Result<Unit>) -> Unit,
    )

    fun deleteProduct(
        productId: Long,
        onResult: (Result<Long>) -> Unit,
    )
}
