package woowacourse.shopping.data.repository.cart

import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product

interface CartRepository {
    fun getAllProductsSize(onResult: (Int) -> Unit)

    fun getProducts(
        limit: Int,
        offset: Int,
        onResult: (List<CartItem>) -> Unit,
    )

    fun addProduct(
        product: Product,
        count: Int,
    )

    fun deleteProduct(productId: Long)

    fun updateCartItem(cartItem: CartItem)
}
