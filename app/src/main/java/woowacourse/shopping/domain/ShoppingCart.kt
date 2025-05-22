package woowacourse.shopping.domain

class ShoppingCart(
    products: Set<CartProduct> = emptySet(),
) {
    private val _cartProducts: MutableSet<CartProduct> = products.toMutableSet()
    val cartProducts: Set<CartProduct> get() = _cartProducts

    operator fun contains(product: Product): Boolean = cartProducts.any { it.product == product }

    operator fun plus(product: Product): ShoppingCart {
        val existing = cartProducts.find { it.product == product }
        val updated =
            if (existing == null) {
                cartProducts + CartProduct(product = product)
            } else {
                cartProducts - existing + existing.copy(quantity = existing.quantity + 1)
            }
        return ShoppingCart(updated)
    }

    operator fun plus(cartProduct: List<CartProduct>): ShoppingCart = ShoppingCart(cartProducts + cartProduct)

    operator fun minus(product: Product): ShoppingCart {
        val existing =
            cartProducts.find { it.product == product }
                ?: throw IllegalArgumentException(ERROR_PRODUCT_NOT_EXIST)
        val updated =
            if (existing.quantity == 1) {
                cartProducts - existing
            } else {
                cartProducts - existing + existing.copy(quantity = existing.quantity - 1)
            }
        return ShoppingCart(updated)
    }

    operator fun minus(cartProduct: CartProduct): ShoppingCart = ShoppingCart(cartProducts - cartProduct)

    fun getQuantity(product: Product): Int = cartProducts.find { it.product.id == product.id }?.quantity ?: 0

    companion object {
        private const val ERROR_PRODUCT_NOT_EXIST = "[ERROR] 장바구니에 해당 상품이 존재하지 않습니다."
    }
}
