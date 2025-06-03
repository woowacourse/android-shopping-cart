package woowacourse.shopping.data.source.cart

import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product

interface CartStorage {
    fun getAllProducts(onResult: (List<CartStorageItem>) -> Unit)

    fun getAllProductsSize(onResult: (Int) -> Unit)

    fun getProducts(
        limit: Int,
        offset: Int,
        onResult: (List<CartStorageItem>) -> Unit,
    )

    fun addProduct(
        product: Product,
        count: Int,
    )

    fun deleteProduct(cartItemId: Long)

    fun updateCartItem(cartItem: CartItem)
}
