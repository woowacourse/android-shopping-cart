package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.model.Product

object CartDummyRepositoryImpl {
    private val cart: MutableList<Product> = mutableListOf<Product>()

    fun fetchCartProducts(): List<Product> = cart.toList()

    fun addCartProduct(product: Product) {
        cart.add(product)
    }

    fun removeCartProduct(product: Product) {
        cart.remove(product)
    }
}
