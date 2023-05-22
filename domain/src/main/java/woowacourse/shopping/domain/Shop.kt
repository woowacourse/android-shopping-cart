package woowacourse.shopping.domain

data class Shop(val products: List<CartProduct>) {
    fun add(cartProduct: CartProduct): Shop {
        return Shop(products + cartProduct)
    }

    fun remove(cartProduct: CartProduct): Shop {
        return Shop(products - cartProduct)
    }
}
