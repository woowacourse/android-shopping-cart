package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.model.Product

interface CartRepository {
    fun getCartItems(callback: (List<Product>) -> Unit)

    fun deleteCartItem(id: Long)

    fun addCartItem(
        product: Product,
        callback: () -> (Unit),
    )
}
