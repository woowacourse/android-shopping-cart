package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product

interface ProductRepository {
    fun getProducts(): List<Product>

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
}
