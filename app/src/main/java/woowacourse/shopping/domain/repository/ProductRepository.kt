package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product

interface ProductRepository {
    fun getProducts(): List<Product>

    fun getPagedProducts(
        page: Int,
        pageSize: Int,
    ): List<Product>

    fun getCartItems(onResult: (Result<List<CartItem>>) -> Unit)
}
