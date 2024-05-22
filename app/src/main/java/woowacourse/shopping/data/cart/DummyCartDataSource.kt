package woowacourse.shopping.data.cart

import woowacourse.shopping.data.shopping.DummyShoppingDataSource
import woowacourse.shopping.domain.entity.Cart
import woowacourse.shopping.domain.entity.CartProduct
import woowacourse.shopping.domain.entity.Product

object DummyCartDataSource : CartDataSource {
    private const val START_PAGE = 1

    private var cart =
        Cart(
            DummyShoppingDataSource.products.take(30),
        )
    private val products: List<CartProduct> get() = cart.cartProducts()

    override fun loadCartProducts(
        currentPage: Int,
        productSize: Int,
    ): List<CartProduct> {
        if (canLoadMoreCartProducts(currentPage, productSize).not()) return emptyList()
        val startIndex = (currentPage - 1) * productSize
        val endIndex = (startIndex + productSize).coerceAtMost(products.size)

        if (startIndex >= products.size) return emptyList()
        return products.subList(startIndex, endIndex)
    }

    override fun addCartProduct(product: Product): Long {
        cart = cart.add(product)
        return product.id
    }

    override fun deleteCartProduct(product: Product): Long {
        cart = cart.remove(product)
        return product.id
    }

    override fun canLoadMoreCartProducts(
        currentPage: Int,
        pageSize: Int,
    ): Boolean {
        if (currentPage < START_PAGE) return false
        val startIndex = (currentPage - 1) * pageSize
        return startIndex < products.size
    }
}
