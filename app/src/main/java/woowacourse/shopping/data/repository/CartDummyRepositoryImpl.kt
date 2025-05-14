package woowacourse.shopping.data.repository

import woowacourse.shopping.domain.model.Product

object CartDummyRepositoryImpl {
    private val cart: MutableList<Product> = mutableListOf<Product>()

    fun fetchCartProducts(
        count: Int,
        lastId: Int,
    ): List<Product> = cart.filter { it.id > lastId }.take(count)

    fun addCartProduct(product: Product) {
        cart.add(product.copy(id = (cart.maxOfOrNull { it.id } ?: 0) + 1))
    }

    fun removeCartProduct(id: Int) {
        cart.removeIf { it.id == id }
    }
}
