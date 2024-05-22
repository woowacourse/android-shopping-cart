package woowacourse.shopping.data.cart

import woowacourse.shopping.data.shopping.ShoppingDataSource
import woowacourse.shopping.domain.entity.CartProduct
import woowacourse.shopping.domain.repository.CartRepository

class DefaultCartRepository(
    private val cartDataSource: CartDataSource,
    private val shoppingDataSource: ShoppingDataSource,
) : CartRepository {
    override fun cartProducts(
        currentPage: Int,
        pageSize: Int,
    ): List<CartProduct> {
        return cartDataSource.loadCartProducts(currentPage, pageSize)
    }

    override fun addCartProduct(productId: Long): Long? {
        val product = shoppingDataSource.productById(productId) ?: return null
        return cartDataSource.addCartProduct(product)
    }

    override fun deleteCartProduct(productId: Long): Long? {
        val product = shoppingDataSource.productById(productId) ?: return null
        return cartDataSource.deleteCartProduct(product)
    }

    override fun canLoadMoreCartProducts(
        currentPage: Int,
        pageSize: Int,
    ): Boolean {
        return cartDataSource.canLoadMoreCartProducts(currentPage, pageSize)
    }
}
