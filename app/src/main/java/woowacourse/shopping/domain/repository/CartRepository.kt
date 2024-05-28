package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.entity.CartProduct

interface CartRepository {
    fun cartProducts(
        currentPage: Int,
        pageSize: Int,
    ): Result<List<CartProduct>>

    fun filterCartProducts(ids: List<Long>): Result<List<CartProduct>>

    fun updateCartProduct(
        productId: Long,
        count: Int,
    ): Result<Long>

    fun deleteCartProduct(productId: Long): Result<Long>

    fun canLoadMoreCartProducts(
        currentPage: Int,
        pageSize: Int,
    ): Result<Boolean>
}
