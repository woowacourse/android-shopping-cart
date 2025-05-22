package woowacourse.shopping.data.cartRepository

import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.Product

interface CartRepository {
    fun getAllProductsSize(onResult: (Int) -> Unit)

    fun getProducts(
        limit: Int,
        offset: Int,
        onResult: (List<CartItem>) -> Unit,
    )

    fun addProduct(product: Product)

    fun deleteProduct(cartItemId: Long)
}
