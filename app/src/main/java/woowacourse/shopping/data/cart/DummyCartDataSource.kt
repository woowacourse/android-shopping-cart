package woowacourse.shopping.data.cart

import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.ShoppingCart

object DummyCartDataSource : CartDataSource {
    private const val PRODUCT_AMOUNT = 5
    private var cart = ShoppingCart()
    private val products: List<CartProduct> get() = cart.cartProducts()

    override fun loadCartProducts(currentPage: Int): List<CartProduct> {
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
}

