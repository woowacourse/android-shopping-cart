package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product

interface ProductRepository {
    fun start(onResult: (Result<Unit>) -> Unit)

    fun fetchPagingProducts(
        page: Int,
        pageSize: Int,
        onResult: (Result<List<CartItem>>) -> Unit,
    )

    fun fetchProducts(): List<Product>

    fun fetchProductById(productId: Long): Product
    
    fun fetchCartItems(onResult: (Result<List<CartItem>>) -> Unit)

    fun fetchPagedCartItems(
        page: Int,
        pageSize: Int,
        onResult: (Result<List<CartItem>>) -> Unit,
    )

    fun shutdown(onResult: (Result<Unit>) -> Unit)
}
