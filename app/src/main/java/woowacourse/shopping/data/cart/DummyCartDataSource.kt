package woowacourse.shopping.data.cart

import android.content.Context
import woowacourse.shopping.data.shopping.DummyShoppingDataSource
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product

class DummyCartDataSource(context: Context) : CartDataSource {
    private val cartDao = CartDatabase.getInstance(context).dao()
    private val PRODUCT_AMOUNT = 5
    private var cart =
        Cart(
            DummyShoppingDataSource.products.take(30),
        )
    private val products: List<CartProduct> get() = cart.cartProducts()

    override fun loadCartProducts(currentPage: Int): List<CartProduct> {
        if ((currentPage - 1) * PRODUCT_AMOUNT >= products.size) return emptyList()
        val startIndex = (currentPage - 1) * PRODUCT_AMOUNT
        val endIndex = (startIndex + PRODUCT_AMOUNT).coerceAtMost(products.size)
        return products.subList(startIndex, endIndex)
    }

    override fun addCartProduct(product: Product): Long? {
        cart = cart.add(product)
        return product.id
    }

    override fun deleteCartProduct(product: Product): Long? {
        cart = cart.remove(product)
        return product.id
    }

    override fun canLoadMoreCartProducts(currentPage: Int): Boolean {
        return loadCartProducts(currentPage + 1).isNotEmpty()
    }
}
