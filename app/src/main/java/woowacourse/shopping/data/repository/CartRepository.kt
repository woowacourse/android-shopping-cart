package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.model.CartProduct
import woowacourse.shopping.domain.model.Product

interface CartRepository {
    fun fetchCartProducts(page: Int, onSuccess: (List<CartProduct>) -> Unit)
    fun fetchAllProduct(onSuccess: (List<CartProduct>) -> Unit)
    fun fetchMaxPageCount(onSuccess: (Int) -> Unit)
    fun upsertCartProduct(product: Product, count: Int)
    fun removeCartProduct(id: Int)
    fun clearCart()
}
