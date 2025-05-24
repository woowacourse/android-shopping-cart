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

    fun fetchProducts(onResult: (Result<List<Product>>) -> Unit)

    fun fetchProductById(
        productId: Long,
        onResult: (Result<Product>) -> Unit,
    )

    fun fetchCartItems(onResult: (Result<List<CartItem>>) -> Unit)

    fun fetchPagedCartItems(
        page: Int,
        pageSize: Int,
        onResult: (Result<List<CartItem>>) -> Unit,
    )

    fun shutdown(onResult: (Result<Unit>) -> Unit)
}
