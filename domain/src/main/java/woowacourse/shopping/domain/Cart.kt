package woowacourse.shopping.domain

data class Cart(val cartProducts: List<CartProduct>) {
    fun add(cartProduct: CartProduct): Cart {
        return Cart(cartProducts + cartProduct)
    }

    fun remove(cartProduct: CartProduct): Cart {
        return Cart(cartProducts - cartProduct)
    }

    fun getSubCart(from: Int, to: Int): Cart {
        val to = if (to <= cartProducts.size) to else cartProducts.size
        return Cart(cartProducts.subList(from, to))
    }

    fun replaceCartProduct(prev: CartProduct, new: CartProduct): Cart {
        return Cart(
            cartProducts.map {
                if (it == prev) new
                else it
            }
        )
    }

    fun removeCartProduct(cartProduct: CartProduct): Cart {
        return Cart(cartProducts.filter { it != cartProduct })
    }
}
