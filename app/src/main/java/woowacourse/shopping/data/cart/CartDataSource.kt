package woowacourse.shopping.data.cart

import woowacourse.shopping.data.cart.model.CartData
import woowacourse.shopping.domain.entity.CartProduct

interface CartDataSource {
    fun loadCarts(
        currentPage: Int,
        productSize: Int,
    ): Result<List<CartData>>

    fun filterCartProducts(ids: List<Long>): Result<List<CartData>>

    fun addCartProduct(product: CartProduct): Result<Long>

    fun deleteCartProduct(productId: Long): Result<Long>

    fun canLoadMoreCart(size: Int): Result<Boolean>
}
