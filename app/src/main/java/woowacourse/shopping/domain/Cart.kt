package woowacourse.shopping.domain

class Cart(val cartItems: List<CartItem> = emptyList()) {
    val totalQuantity: Quantity = cartItems.fold(Quantity(0)) { total, item ->
        total + item.quantity
    }

    fun plusCartItem(cartItem: CartItem): Cart {
        val isSameCartItem = cartItems.any { it == cartItem }

        if (isSameCartItem) {
            return this
        }
        return Cart(cartItems + cartItem)
    }

    fun removeCartItems(ids: List<String>): Cart = Cart(
        cartItems.filter { cartItem ->
            ids.none {
                cartItem.hasProductId(it)
            }
        },
    )

    fun contains(product: Product): Boolean = cartItems.any { it.hasProduct(product) }

    fun getQuantity(product: Product): Quantity? {
        val findItem = cartItems.find { it.hasProduct(product) } ?: return null
        return findItem.quantity
    }

    fun plusProduct(
        product: Product,
        quantity: Quantity,
    ): Cart {
        val findItem = cartItems.find { it.hasProduct(product) }

        return if (findItem == null) Cart(cartItems + CartItem(product, quantity))
        else {
            val updateItems = cartItems.map { cartItem ->
                if (cartItem.hasProduct(product)) cartItem.increase(quantity)
                else cartItem
            }
            Cart(updateItems)
        }
    }

    fun minusProduct(
        product: Product,
        quantity: Quantity,
    ): Cart {
        val findItem = cartItems.find { it.hasProduct(product) } ?: throw IllegalArgumentException("해당 상품을 찾을 수 없습니다")
        return when {
            findItem.isQuantityLessThan(quantity) -> throw IllegalArgumentException("삭제 수량이 보유 수량보다 많습니다")
            findItem.isSameQuantity(quantity) -> Cart(cartItems.filterNot { it.hasProduct(product) })
            else -> {
                val updateItems = cartItems.map { cartItem ->
                    if (cartItem.hasProduct(product)) cartItem.decrease(quantity)
                    else cartItem
                }
                Cart(updateItems)
            }
        }
    }

    fun getProductList(): List<Product> = cartItems.map { it.product }
}
