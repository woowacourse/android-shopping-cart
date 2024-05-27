package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.CartProduct

interface CartRepository {
    fun totalCartProducts(): List<CartProduct>

    fun cartProducts(currentPage: Int): List<CartProduct>

    fun addCartProduct(
        productId: Long,
        count: Int,
    ): Long?

    fun deleteCartProduct(productId: Long): Long?

    fun canLoadMoreCartProducts(currentPage: Int): Boolean
}
