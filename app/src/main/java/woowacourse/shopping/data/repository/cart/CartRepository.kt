package woowacourse.shopping.data.repository.cart

import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartContent
import woowacourse.shopping.domain.Product

interface CartRepository {
    suspend fun loadCart(): Cart
    suspend fun loadCartContents(): List<CartContent>
    suspend fun loadCartSize(): Int
    suspend fun increase(product: Product)
    suspend fun decrease(productId: String)
    suspend fun pagination(
        startIndex: Int,
        pageSize: Int,
    ): List<CartContent>
    suspend fun remove(productId: String)
    suspend fun setProductQuantity(
        productId: String,
        quantity: Int,
    )
}
