package woowacourse.shopping.view.cart

import woowacourse.shopping.model.products.Product
import woowacourse.shopping.model.products.ShoppingCartItem

interface CartRepository {
    fun add(product: Product)

    fun updateQuantity(
        productId: String,
        quantity: Int,
    )

    fun getAllShoppingCartItem(): List<ShoppingCartItem>

    fun remove(product: Product)

    fun clear()
}
