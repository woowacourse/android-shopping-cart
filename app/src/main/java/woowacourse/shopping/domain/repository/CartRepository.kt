package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.entity.CartProduct

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
