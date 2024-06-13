package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.CartProduct

interface CartRepository {
    fun totalCartProducts(): List<CartProduct>

    fun cartProducts(currentPage: Int): List<CartProduct>

    fun addCartProduct(
        productId: Long,
        count: Int,
    )

    fun deleteCartProduct(productId: Long)

    fun canLoadMoreCartProducts(currentPage: Int): Boolean
}
