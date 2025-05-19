package woowacourse.shopping.domain.cart

import woowacourse.shopping.domain.product.Product

interface CartRepository {
    fun fetchInRange(
        limit: Int,
        offset: Int,
        onResult: (List<CartProduct>) -> Unit,
    )

    fun fetchById(
        cartItemId: Long,
        onResult: (CartProduct) -> Unit,
    )

    fun insert(product: Product)

    fun delete(cartItemId: Long)
}
