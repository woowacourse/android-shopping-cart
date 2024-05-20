package woowacourse.shopping.data.cart

import woowacourse.shopping.data.shopping.DummyShoppingDataSource
import woowacourse.shopping.data.shopping.ShoppingDataSource
import woowacourse.shopping.domain.CartProduct

class DefaultCartRepository(
    private val cartDataSource: CartDataSource = DummyCartDataSource,
    private val shoppingDataSource: ShoppingDataSource = DummyShoppingDataSource,
) : CartRepository {
    override fun cartProducts(currentPage: Int): List<CartProduct> {
        return cartDataSource.loadCartProducts(currentPage)
    }

    override fun addCartProduct(productId: Long): Long? {
        val product = shoppingDataSource.productById(productId) ?: return null
        return cartDataSource.addCartProduct(product)
    }

    override fun deleteCartProduct(productId: Long): Long? {
        val product = shoppingDataSource.productById(productId) ?: return null
        return cartDataSource.deleteCartProduct(product)
    }

    override fun canLoadMoreCartProducts(currentPage: Int): Boolean {
        return cartDataSource.canLoadMoreCartProducts(currentPage)
    }
}
