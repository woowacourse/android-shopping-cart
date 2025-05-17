package woowacourse.shopping.domain

interface ProductRepository {
    fun getProducts(): List<Product>

    fun getPagedProducts(
        page: Int,
        pageSize: Int,
    ): List<Product>

    fun getCartProductCount(onComplete: (Int) -> Unit)

    fun getCartProducts(onComplete: (List<Product>) -> Unit)

    fun getPagedCartProducts(
        limit: Int,
        page: Int,
        onComplete: (List<Product>) -> Unit,
    )

    fun insertProduct(product: Product)

    fun deleteProduct(
        productId: Long,
        onComplete: () -> Unit,
    )
}
