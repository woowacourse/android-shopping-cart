package woowacourse.shopping.domain.cart

import woowacourse.shopping.domain.product.Product

data class Cart(
    val cartItems: CartItems = CartItems(),
) {
    val totalQuantity: Int = cartItems.totalQuantity
    val totalPrice: Int = cartItems.totalPrice
    val isEmpty: Boolean = cartItems.size() == 0

    fun addProduct(
        product: Product,
        quantity: Quantity = Quantity.ONE,
    ): Cart = copy(cartItems = cartItems.addProduct(product, quantity))

    fun increase(productId: Int): Cart = copy(cartItems = cartItems.increase(productId))

    fun decrease(productId: Int): Cart = copy(cartItems = cartItems.decrease(productId))

    fun remove(productId: Int): Cart = copy(cartItems = cartItems.remove(productId))

    fun findQuantity(productId: Int): Quantity = cartItems.findQuantity(productId)

    fun getPage(
        page: Int,
        pageSize: Int,
    ): List<CartItem> {
        require(page >= 0) { "page는 0 이상이어야 합니다. page=$page" }
        require(pageSize > 0) { "pageSize는 1 이상이어야 합니다. pageSize=$pageSize" }

        val fromIndex = page * pageSize
        val toIndex = fromIndex + pageSize
        return cartItems.subList(fromIndex, toIndex)
    }
}
