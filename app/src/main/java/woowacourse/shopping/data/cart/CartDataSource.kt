package woowacourse.shopping.data.cart

import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product

interface CartDataSource {
    fun totalCartProducts(): List<CartProduct>

    fun loadCartProducts(currentPage: Int): List<CartProduct>

    fun addCartProduct(
        product: Product,
        count: Int,
    ): Long?

    fun deleteCartProduct(product: Product): Long?

    fun canLoadMoreCartProducts(currentPage: Int): Boolean
}
