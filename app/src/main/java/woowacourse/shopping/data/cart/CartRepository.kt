package woowacourse.shopping.data.cart

import woowacourse.shopping.domain.CartProduct

interface CartRepository {
    fun cartProducts(
        currentPage: Int,
        pageSize: Int,
    ): List<CartProduct>

    fun addCartProduct(productId: Long): Long?

    fun deleteCartProduct(productId: Long): Long?

    fun canLoadMoreCartProducts(
        currentPage: Int,
        pageSize: Int,
    ): Boolean
}
