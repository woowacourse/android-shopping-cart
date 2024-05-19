package woowacourse.shopping.data.cart

import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.product

class FakeCartRepository(
    private var cart: Cart = Cart(),
) : CartRepository {

    private val products get() = cart.cartProducts()

    override fun cartProducts(currentPage: Int, productSize: Int): List<CartProduct> {
        if (canLoadMoreCartProducts(currentPage, productSize).not()) return emptyList()
        val startIndex = (currentPage - 1) * productSize
        val endIndex = (startIndex + productSize).coerceAtMost(products.size)

        if (startIndex >= products.size) return emptyList()
        return products.subList(startIndex, endIndex)
    }

    override fun addCartProduct(productId: Long): Long? {
        cart = cart.add(product(id = productId, name = "오둥이 $productId"))
        return productId
    }

    override fun deleteCartProduct(productId: Long): Long? {
        cart = cart.remove(product(id = productId, name = "오둥이 $productId"))
        return productId
    }

    override fun canLoadMoreCartProducts(currentPage: Int, pageSize: Int): Boolean {
        if (currentPage < 1) return false
        val startIndex = (currentPage - 1) * pageSize
        return startIndex < products.size
    }
}