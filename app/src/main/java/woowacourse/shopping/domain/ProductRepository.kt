package woowacourse.shopping.domain

interface ProductRepository {
    fun getProducts(): List<Product>

    fun getPagedProducts(
        page: Int,
        pageSize: Int,
    ): List<Product>

    fun getCartProducts(): List<Product>

    fun getPagedCartProducts(
        pageSize: Int,
        page: Int,
    ): List<Product>

    fun deleteProduct(productId: Long)
}
