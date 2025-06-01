package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.CartItem
import woowacourse.shopping.domain.model.Product

interface ProductRepository {
    fun fetchPagingProducts(
        page: Int,
        pageSize: Int,
        onResult: (Result<List<CartItem>>) -> Unit,
    )

    fun fetchProductById(
        productId: Long,
        onResult: (Result<Product>) -> Unit,
    )
}
