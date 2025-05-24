package woowacourse.shopping.domain

data class Cart(val product: Product, val quantity: Int) {
    fun increase() = Cart(product, quantity + 1)

    fun decrease() = Cart(product, quantity - 1)
}
