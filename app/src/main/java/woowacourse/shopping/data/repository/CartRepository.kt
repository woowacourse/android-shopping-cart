package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.Product

interface CartRepository {
    fun fetchCartProducts(page: Int, callback: (List<CartProduct>) -> Unit)
    fun fetchAllProduct(callback: (List<CartProduct>) -> Unit)
    fun fetchMaxPageCount(callback: (Int) -> Unit)
    fun upsertCartProduct(product: Product, count: Int)
    fun removeCartProduct(id: Int)
    fun clearCart()
}
